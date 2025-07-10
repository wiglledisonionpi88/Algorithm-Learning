﻿using Microsoft.AspNetCore.Connections;
using ModelContextProtocol.Protocol;
using ModelContextProtocol.Server;
using Serilog;
using System.Diagnostics;
using System.Text;
using System.Text.Json;

#pragma warning disable CS1998 // Async method lacks 'await' operators and will run synchronously

namespace ModelContextProtocol.TestSseServer;

public class Program
{
    private static void ConfigureSerilog(ILoggingBuilder loggingBuilder)
    {
        Log.Logger = new LoggerConfiguration()
            .MinimumLevel.Verbose() // Capture all log levels
            .WriteTo.File(Path.Combine(AppContext.BaseDirectory, "logs", "TestServer_.log"),
                rollingInterval: RollingInterval.Day,
                outputTemplate: "{Timestamp:yyyy-MM-dd HH:mm:ss.fff zzz} [{Level:u3}] {Message:lj}{NewLine}{Exception}")
            .CreateLogger();

        loggingBuilder.AddSerilog();
    }

    public static Task Main(string[] args) => MainAsync(args);

    private static void ConfigureOptions(McpServerOptions options)
    {
        options.Capabilities = new ServerCapabilities
        {
            Tools = new(),
            Resources = new(),
            Prompts = new(),
        };
        options.ServerInstructions = "This is a test server with only stub functionality";

        Console.WriteLine("Registering handlers.");

        #region Helped method
        static CreateMessageRequestParams CreateRequestSamplingParams(string context, string uri, int maxTokens = 100)
        {
            return new CreateMessageRequestParams
            {
                Messages = [new SamplingMessage
                {
                    Role = Role.User,
                    Content = new TextContentBlock { Text = $"Resource {uri} context: {context}" },
                }],
                SystemPrompt = "You are a helpful test server.",
                MaxTokens = maxTokens,
                Temperature = 0.7f,
                IncludeContext = ContextInclusion.ThisServer
            };
        }
        #endregion

        List<Resource> resources = new();
        List<ResourceContents> resourceContents = new();
        for (int i = 0; i < 100; ++i)
        {
            string uri = $"test://static/resource/{i + 1}";
            if (i % 2 == 0)
            {
                resources.Add(new Resource
                {
                    Uri = uri,
                    Name = $"Resource {i + 1}",
                    MimeType = "text/plain"
                });
                resourceContents.Add(new TextResourceContents
                {
                    Uri = uri,
                    MimeType = "text/plain",
                    Text = $"Resource {i + 1}: This is a plaintext resource"
                });
            }
            else
            {
                var buffer = Encoding.UTF8.GetBytes($"Resource {i + 1}: This is a base64 blob");
                resources.Add(new Resource
                {
                    Uri = uri,
                    Name = $"Resource {i + 1}",
                    MimeType = "application/octet-stream"
                });
                resourceContents.Add(new BlobResourceContents
                {
                    Uri = uri,
                    MimeType = "application/octet-stream",
                    Blob = Convert.ToBase64String(buffer)
                });
            }
        }

        const int pageSize = 10;

        options.Capabilities = new()
        {
            Tools = new()
            {
                ListToolsHandler = async (request, cancellationToken) =>
                {
                    return new ListToolsResult
                    {
                        Tools =
                        [
                            new Tool
                            {
                                Name = "echo",
                                Description = "Echoes the input back to the client.",
                                InputSchema = JsonSerializer.Deserialize<JsonElement>("""
                                    {
                                        "type": "object",
                                        "properties": {
                                            "message": {
                                                "type": "string",
                                                "description": "The input to echo back."
                                            }
                                        },
                                        "required": ["message"]
                                    }
                                    """, McpJsonUtilities.DefaultOptions),
                            },
                            new Tool
                            {
                                Name = "echoSessionId",
                                Description = "Echoes the session id back to the client.",
                                InputSchema = JsonSerializer.Deserialize<JsonElement>("""
                                    {
                                        "type": "object"
                                    }
                                    """, McpJsonUtilities.DefaultOptions),
                            },
                            new Tool
                            {
                                Name = "sampleLLM",
                                Description = "Samples from an LLM using MCP's sampling feature.",
                                InputSchema = JsonSerializer.Deserialize<JsonElement>("""
                                    {
                                        "type": "object",
                                        "properties": {
                                            "prompt": {
                                                "type": "string",
                                                "description": "The prompt to send to the LLM"
                                            },
                                            "maxTokens": {
                                                "type": "number",
                                                "description": "Maximum number of tokens to generate"
                                            }
                                        },
                                        "required": ["prompt", "maxTokens"]
                                    }
                                    """, McpJsonUtilities.DefaultOptions),
                            }
                        ]
                    };
                },
                CallToolHandler = async (request, cancellationToken) =>
                {
                    if (request.Params is null)
                    {
                        throw new McpException("Missing required parameter 'name'", McpErrorCode.InvalidParams);
                    }
                    if (request.Params.Name == "echo")
                    {
                        if (request.Params.Arguments is null || !request.Params.Arguments.TryGetValue("message", out var message))
                        {
                            throw new McpException("Missing required argument 'message'", McpErrorCode.InvalidParams);
                        }
                        return new CallToolResult
                        {
                            Content = [new TextContentBlock { Text = $"Echo: {message}" }]
                        };
                    }
                    else if (request.Params.Name == "echoSessionId")
                    {
                        return new CallToolResult
                        {
                            Content = [new TextContentBlock { Text = request.Server.SessionId ?? string.Empty }]
                        };
                    }
                    else if (request.Params.Name == "sampleLLM")
                    {
                        if (request.Params.Arguments is null ||
                            !request.Params.Arguments.TryGetValue("prompt", out var prompt) ||
                            !request.Params.Arguments.TryGetValue("maxTokens", out var maxTokens))
                        {
                            throw new McpException("Missing required arguments 'prompt' and 'maxTokens'", McpErrorCode.InvalidParams);
                        }
                        var sampleResult = await request.Server.SampleAsync(CreateRequestSamplingParams(prompt.ToString(), "sampleLLM", Convert.ToInt32(maxTokens.ToString())),
                            cancellationToken);

                        return new CallToolResult
                        {
                            Content = [new TextContentBlock { Text = $"LLM sampling result: {(sampleResult.Content as TextContentBlock)?.Text}" }]
                        };
                    }
                    else
                    {
                        throw new McpException($"Unknown tool: '{request.Params.Name}'", McpErrorCode.InvalidParams);
                    }
                }
            },
            Resources = new()
            {
                ListResourceTemplatesHandler = async (request, cancellationToken) =>
                {

                    return new ListResourceTemplatesResult
                    {
                        ResourceTemplates = [
                            new ResourceTemplate
                            {
                                UriTemplate = "test://dynamic/resource/{id}",
                                Name = "Dynamic Resource",
                            }
                        ]
                    };
                },

                ListResourcesHandler = async (request, cancellationToken) =>
                {
                    int startIndex = 0;
                    var requestParams = request.Params ?? new();
                    if (requestParams.Cursor is not null)
                    {
                        try
                        {
                            var startIndexAsString = Encoding.UTF8.GetString(Convert.FromBase64String(requestParams.Cursor));
                            startIndex = Convert.ToInt32(startIndexAsString);
                        }
                        catch (Exception e)
                        {
                            throw new McpException($"Invalid cursor: '{requestParams.Cursor}'", e, McpErrorCode.InvalidParams);
                        }
                    }

                    int endIndex = Math.Min(startIndex + pageSize, resources.Count);
                    string? nextCursor = null;

                    if (endIndex < resources.Count)
                    {
                        nextCursor = Convert.ToBase64String(Encoding.UTF8.GetBytes(endIndex.ToString()));
                    }

                    return new ListResourcesResult
                    {
                        NextCursor = nextCursor,
                        Resources = resources.GetRange(startIndex, endIndex - startIndex)
                    };
                },
                ReadResourceHandler = async (request, cancellationToken) =>
                {
                    if (request.Params?.Uri is null)
                    {
                        throw new McpException("Missing required argument 'uri'", McpErrorCode.InvalidParams);
                    }

                    if (request.Params.Uri.StartsWith("test://dynamic/resource/"))
                    {
                        var id = request.Params.Uri.Split('/').LastOrDefault();
                        if (string.IsNullOrEmpty(id))
                        {
                            throw new McpException($"Invalid resource URI: '{request.Params.Uri}'", McpErrorCode.InvalidParams);
                        }

                        return new ReadResourceResult
                        {
                            Contents = [
                                new TextResourceContents
                                {
                                    Uri = request.Params.Uri,
                                    MimeType = "text/plain",
                                    Text = $"Dynamic resource {id}: This is a plaintext resource"
                                }
                            ]
                        };
                    }

                    ResourceContents? contents = resourceContents.FirstOrDefault(r => r.Uri == request.Params.Uri) ??
                        throw new McpException($"Resource not found: '{request.Params.Uri}'", McpErrorCode.InvalidParams);

                    return new ReadResourceResult
                    {
                        Contents = [contents]
                    };
                }
            },
            Prompts = new()
            {
                ListPromptsHandler = async (request, cancellationToken) =>
                {
                    return new ListPromptsResult
                    {
                        Prompts = [
                            new Prompt
                            {
                                Name = "simple_prompt",
                                Description = "A prompt without arguments"
                            },
                            new Prompt
                            {
                                Name = "complex_prompt",
                                Description = "A prompt with arguments",
                                Arguments =
                                [
                                    new PromptArgument
                                    {
                                        Name = "temperature",
                                        Description = "Temperature setting",
                                        Required = true
                                    },
                                    new PromptArgument
                                    {
                                        Name = "style",
                                        Description = "Output style",
                                        Required = false
                                    }
                                ],
                            }
                        ]
                    };
                },
                GetPromptHandler = async (request, cancellationToken) =>
                {
                    if (request.Params is null)
                    {
                        throw new McpException("Missing required parameter 'name'", McpErrorCode.InvalidParams);
                    }
                    List<PromptMessage> messages = new();
                    if (request.Params.Name == "simple_prompt")
                    {
                        messages.Add(new PromptMessage
                        {
                            Role = Role.User,
                            Content = new TextContentBlock { Text = "This is a simple prompt without arguments." },
                        });
                    }
                    else if (request.Params.Name == "complex_prompt")
                    {
                        string temperature = request.Params.Arguments?["temperature"].ToString() ?? "unknown";
                        string style = request.Params.Arguments?["style"].ToString() ?? "unknown";
                        messages.Add(new PromptMessage
                        {
                            Role = Role.User,
                            Content = new TextContentBlock { Text = $"This is a complex prompt with arguments: temperature={temperature}, style={style}" },
                        });
                        messages.Add(new PromptMessage
                        {
                            Role = Role.Assistant,
                            Content = new TextContentBlock { Text = "I understand. You've provided a complex prompt with temperature and style arguments. How would you like me to proceed?" },
                        });
                        messages.Add(new PromptMessage
                        {
                            Role = Role.User,
                            Content = new ImageContentBlock
                            {
                                Data = MCP_TINY_IMAGE,
                                MimeType = "image/png"
                            }
                        });
                    }
                    else
                    {
                        throw new McpException($"Unknown prompt: {request.Params.Name}", McpErrorCode.InvalidParams);
                    }

                    return new GetPromptResult
                    {
                        Messages = messages
                    };
                }
            },
        };
    }

    private static void HandleStatelessMcp(IApplicationBuilder app)
    {
        var serviceCollection = new ServiceCollection();
        serviceCollection.AddLogging();
        serviceCollection.AddSingleton(app.ApplicationServices.GetRequiredService<ILoggerFactory>());
        serviceCollection.AddSingleton(app.ApplicationServices.GetRequiredService<DiagnosticListener>());
        serviceCollection.AddRoutingCore();

        serviceCollection.AddMcpServer(ConfigureOptions).WithHttpTransport(options => options.Stateless = true);

        var appBuilder = new ApplicationBuilder(serviceCollection.BuildServiceProvider());
        appBuilder.UseRouting();
        appBuilder.UseEndpoints(innerEndpoints =>
        {
            innerEndpoints.MapMcp("/stateless");
        });

        app.Run(appBuilder.Build());
    }

    public static async Task MainAsync(string[] args, ILoggerProvider? loggerProvider = null, IConnectionListenerFactory? kestrelTransport = null, CancellationToken cancellationToken = default)
    {
        Console.WriteLine("Starting server...");

        var builder = WebApplication.CreateEmptyBuilder(new()
        {
            Args = args,
        });

        if (kestrelTransport is null)
        {
            int port = args.Length > 0 && uint.TryParse(args[0], out var parsedPort) ? (int)parsedPort : 3001;
            builder.WebHost.ConfigureKestrel(options =>
            {
                options.ListenLocalhost(port);
            });
        }
        else
        {
            // Add passed-in transport before calling UseKestrelCore() to avoid the SocketsHttpHandler getting added.
            builder.Services.AddSingleton(kestrelTransport);
        }

        builder.WebHost.UseKestrelCore();
        builder.Services.AddLogging();
        builder.Services.AddRoutingCore();

        builder.Logging.AddConsole();
        ConfigureSerilog(builder.Logging);
        if (loggerProvider is not null)
        {
            builder.Logging.AddProvider(loggerProvider);
        }

        builder.Services.AddMcpServer(ConfigureOptions)
            .WithHttpTransport();

        var app = builder.Build();
        app.UseRouting();
        app.UseEndpoints(_ => { });

        // Handle the /stateless endpoint if no other endpoints have been matched by the call to UseRouting above.
        HandleStatelessMcp(app);

        app.MapMcp();

        await app.RunAsync(cancellationToken);
    }

    const string MCP_TINY_IMAGE =
  "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";
}