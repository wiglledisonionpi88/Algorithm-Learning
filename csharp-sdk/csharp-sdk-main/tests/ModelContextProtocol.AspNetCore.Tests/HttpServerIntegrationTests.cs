﻿using ModelContextProtocol.Client;
using ModelContextProtocol.Protocol;
using ModelContextProtocol.Tests.Utils;

namespace ModelContextProtocol.AspNetCore.Tests;

public abstract class HttpServerIntegrationTests : LoggedTest, IClassFixture<SseServerIntegrationTestFixture>
{
    protected readonly SseServerIntegrationTestFixture _fixture;

    public HttpServerIntegrationTests(SseServerIntegrationTestFixture fixture, ITestOutputHelper testOutputHelper)
        : base(testOutputHelper)
    {
        _fixture = fixture;
        _fixture.Initialize(testOutputHelper, ClientTransportOptions);
    }

    public override void Dispose()
    {
        _fixture.TestCompleted();
        base.Dispose();
    }

    protected abstract SseClientTransportOptions ClientTransportOptions { get; }

    private Task<IMcpClient> GetClientAsync(McpClientOptions? options = null)
    {
        return _fixture.ConnectMcpClientAsync(options, LoggerFactory);
    }

    [Fact]
    public async Task ConnectAndPing_Sse_TestServer()
    {
        // Arrange

        // Act
        await using var client = await GetClientAsync();
        await client.PingAsync(TestContext.Current.CancellationToken);

        // Assert
        Assert.NotNull(client);
    }

    [Fact]
    public async Task Connect_TestServer_ShouldProvideServerFields()
    {
        // Arrange

        // Act
        await using var client = await GetClientAsync();

        // Assert
        Assert.NotNull(client.ServerCapabilities);
        Assert.NotNull(client.ServerInfo);

        if (ClientTransportOptions.Endpoint.AbsolutePath.EndsWith("/sse"))
        {
            Assert.Null(client.SessionId);
        }
        else
        {
            Assert.NotNull(client.SessionId);
        }
    }

    [Fact]
    public async Task ListTools_Sse_TestServer()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        var tools = await client.ListToolsAsync(cancellationToken: TestContext.Current.CancellationToken);

        // assert
        Assert.NotNull(tools);
    }

    [Fact]
    public async Task CallTool_Sse_EchoServer()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        var result = await client.CallToolAsync(
            "echo",
            new Dictionary<string, object?>
            {
                ["message"] = "Hello MCP!"
            },
            cancellationToken: TestContext.Current.CancellationToken
        );

        // assert
        Assert.NotNull(result);
        Assert.Null(result.IsError);
        var textContent = Assert.Single(result.Content.OfType<TextContentBlock>());
        Assert.Equal("Echo: Hello MCP!", textContent.Text);
    }

    [Fact]
    public async Task CallTool_EchoSessionId_ReturnsTheSameSessionId()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        var result1 = await client.CallToolAsync("echoSessionId", cancellationToken: TestContext.Current.CancellationToken);
        var result2 = await client.CallToolAsync("echoSessionId", cancellationToken: TestContext.Current.CancellationToken);
        var result3 = await client.CallToolAsync("echoSessionId", cancellationToken: TestContext.Current.CancellationToken);

        // assert
        Assert.NotNull(result1);
        Assert.NotNull(result2);
        Assert.NotNull(result3);

        Assert.Null(result1.IsError);
        Assert.Null(result2.IsError);
        Assert.Null(result3.IsError);

        var textContent1 = Assert.Single(result1.Content.OfType<TextContentBlock>());
        var textContent2 = Assert.Single(result2.Content.OfType<TextContentBlock>());
        var textContent3 = Assert.Single(result3.Content.OfType<TextContentBlock>());

        Assert.NotNull(textContent1.Text);
        Assert.Equal(textContent1.Text, textContent2.Text);
        Assert.Equal(textContent1.Text, textContent3.Text);
    }

    [Fact]
    public async Task ListResources_Sse_TestServer()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();

        IList<McpClientResource> allResources = await client.ListResourcesAsync(TestContext.Current.CancellationToken);

        // The everything server provides 100 test resources
        Assert.Equal(100, allResources.Count);
    }

    [Fact]
    public async Task ReadResource_Sse_TextResource()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        // Odd numbered resources are text in the everything server (despite the docs saying otherwise)
        // 1 is index 0, which is "even" in the 0-based index
        // We copied this oddity to the test server
        var result = await client.ReadResourceAsync("test://static/resource/1", TestContext.Current.CancellationToken);

        Assert.NotNull(result);
        Assert.Single(result.Contents);

        TextResourceContents textContent = Assert.IsType<TextResourceContents>(result.Contents[0]);
        Assert.NotNull(textContent.Text);
    }

    [Fact]
    public async Task ReadResource_Sse_BinaryResource()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        // Even numbered resources are binary in the everything server (despite the docs saying otherwise)
        // 2 is index 1, which is "odd" in the 0-based index
        // We copied this oddity to the test server
        var result = await client.ReadResourceAsync("test://static/resource/2", TestContext.Current.CancellationToken);

        Assert.NotNull(result);
        Assert.Single(result.Contents);

        BlobResourceContents blobContent = Assert.IsType<BlobResourceContents>(result.Contents[0]);
        Assert.NotNull(blobContent.Blob);
    }

    [Fact]
    public async Task ListPrompts_Sse_TestServer()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        var prompts = await client.ListPromptsAsync(TestContext.Current.CancellationToken);

        // assert
        Assert.NotNull(prompts);
        Assert.NotEmpty(prompts);
        // We could add specific assertions for the known prompts
        Assert.Contains(prompts, p => p.Name == "simple_prompt");
        Assert.Contains(prompts, p => p.Name == "complex_prompt");
    }

    [Fact]
    public async Task GetPrompt_Sse_SimplePrompt()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        var result = await client.GetPromptAsync("simple_prompt", null, cancellationToken: TestContext.Current.CancellationToken);

        // assert
        Assert.NotNull(result);
        Assert.NotEmpty(result.Messages);
    }

    [Fact]
    public async Task GetPrompt_Sse_ComplexPrompt()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        var arguments = new Dictionary<string, object?>
        {
            { "temperature", "0.7" },
            { "style", "formal" }
        };
        var result = await client.GetPromptAsync("complex_prompt", arguments, cancellationToken: TestContext.Current.CancellationToken);

        // assert
        Assert.NotNull(result);
        Assert.NotEmpty(result.Messages);
    }

    [Fact]
    public async Task GetPrompt_Sse_NonExistent_ThrowsException()
    {
        // arrange

        // act
        await using var client = await GetClientAsync();
        await Assert.ThrowsAsync<McpException>(async () => await client.GetPromptAsync("non_existent_prompt", null, cancellationToken: TestContext.Current.CancellationToken));
    }

    [Fact]
    public async Task Sampling_Sse_TestServer()
    {
        Assert.SkipWhen(GetType() == typeof(StatelessServerIntegrationTests), "Sampling is not supported in stateless mode.");

        // arrange
        // Set up the sampling handler
        int samplingHandlerCalls = 0;
#pragma warning disable CS1998 // Async method lacks 'await' operators and will run synchronously
        McpClientOptions options = new();
        options.Capabilities = new();
        options.Capabilities.Sampling ??= new();
        options.Capabilities.Sampling.SamplingHandler = async (_, _, _) =>
        {
            samplingHandlerCalls++;
            return new CreateMessageResult
            {
                Model = "test-model",
                Role = Role.Assistant,
                Content = new TextContentBlock { Text = "Test response" },
            };
        };
        await using var client = await GetClientAsync(options);
#pragma warning restore CS1998 // Async method lacks 'await' operators and will run synchronously

        // Call the server's sampleLLM tool which should trigger our sampling handler
        var result = await client.CallToolAsync("sampleLLM", new Dictionary<string, object?>
        {
            ["prompt"] = "Test prompt",
            ["maxTokens"] = 100
        },
            cancellationToken: TestContext.Current.CancellationToken);

        // assert
        Assert.NotNull(result);
        var textContent = Assert.Single(result.Content.OfType<TextContentBlock>());
        Assert.False(string.IsNullOrEmpty(textContent.Text));
    }

    [Fact]
    public async Task CallTool_Sse_EchoServer_Concurrently()
    {
        await using var client1 = await GetClientAsync();
        await using var client2 = await GetClientAsync();

        for (int i = 0; i < 4; i++)
        {
            var client = (i % 2 == 0) ? client1 : client2;
            var result = await client.CallToolAsync(
                "echo",
                new Dictionary<string, object?>
                {
                    ["message"] = $"Hello MCP! {i}"
                },
                cancellationToken: TestContext.Current.CancellationToken
            );

            Assert.NotNull(result);
            Assert.Null(result.IsError);
            var textContent = Assert.Single(result.Content.OfType<TextContentBlock>());
            Assert.Equal($"Echo: Hello MCP! {i}", textContent.Text);
        }
    }
}
