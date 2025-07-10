﻿using Microsoft.Extensions.AI;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Primitives;
using ModelContextProtocol.Protocol;
using ModelContextProtocol.Server;
using Moq;
using System.ComponentModel;
using System.Diagnostics;
using System.Reflection;
using System.Text.Json;
using System.Text.Json.Nodes;

namespace ModelContextProtocol.Tests.Server;

public class McpServerPromptTests
{
    [Fact]
    public void Create_InvalidArgs_Throws()
    {
        Assert.Throws<ArgumentNullException>("function", () => McpServerPrompt.Create((AIFunction)null!));
        Assert.Throws<ArgumentNullException>("method", () => McpServerPrompt.Create((MethodInfo)null!));
        Assert.Throws<ArgumentNullException>("method", () => McpServerPrompt.Create((MethodInfo)null!, _ => new object()));
        Assert.Throws<ArgumentNullException>("createTargetFunc", () => McpServerPrompt.Create(typeof(McpServerPromptTests).GetMethod(nameof(Create_InvalidArgs_Throws))!, null!));
        Assert.Throws<ArgumentNullException>("method", () => McpServerPrompt.Create((Delegate)null!));
    }

    [Fact]
    public async Task SupportsIMcpServer()
    {
        Mock<IMcpServer> mockServer = new();

        McpServerPrompt prompt = McpServerPrompt.Create((IMcpServer server) =>
        {
            Assert.Same(mockServer.Object, server);
            return new ChatMessage(ChatRole.User, "Hello");
        });

        Assert.DoesNotContain("server", prompt.ProtocolPrompt.Arguments?.Select(a => a.Name) ?? []);

        var result = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(mockServer.Object),
            TestContext.Current.CancellationToken);
        Assert.NotNull(result);
        Assert.NotNull(result.Messages);
        Assert.Single(result.Messages);
        Assert.Equal("Hello", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    [Fact]
    public async Task SupportsCtorInjection()
    {
        MyService expectedMyService = new();

        ServiceCollection sc = new();
        sc.AddSingleton(expectedMyService);
        IServiceProvider services = sc.BuildServiceProvider();

        Mock<IMcpServer> mockServer = new();
        mockServer.SetupGet(s => s.Services).Returns(services);

        MethodInfo? testMethod = typeof(HasCtorWithSpecialParameters).GetMethod(nameof(HasCtorWithSpecialParameters.TestPrompt));
        Assert.NotNull(testMethod);
        McpServerPrompt prompt = McpServerPrompt.Create(testMethod, r =>
        {
            Assert.NotNull(r.Services);
            return ActivatorUtilities.CreateInstance(r.Services, typeof(HasCtorWithSpecialParameters));
        }, new() { Services = services });

        var result = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(mockServer.Object),
            TestContext.Current.CancellationToken);
        Assert.NotNull(result);
        Assert.NotNull(result.Messages);
        Assert.Single(result.Messages);
        Assert.Equal("True True True True", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    private sealed class HasCtorWithSpecialParameters
    {
        private readonly MyService _ms;
        private readonly IMcpServer _server;
        private readonly RequestContext<GetPromptRequestParams> _request;
        private readonly IProgress<ProgressNotificationValue> _progress;

        public HasCtorWithSpecialParameters(MyService ms, IMcpServer server, RequestContext<GetPromptRequestParams> request, IProgress<ProgressNotificationValue> progress)
        {
            Assert.NotNull(ms);
            Assert.NotNull(server);
            Assert.NotNull(request);
            Assert.NotNull(progress);

            _ms = ms;
            _server = server;
            _request = request;
            _progress = progress;
        }

        public string TestPrompt() => $"{_ms is not null} {_server is not null} {_request is not null} {_progress is not null}";
    }

    [Fact]
    public async Task SupportsServiceFromDI()
    {
        MyService expectedMyService = new();

        ServiceCollection sc = new();
        sc.AddSingleton(expectedMyService);
        IServiceProvider services = sc.BuildServiceProvider();

        McpServerPrompt prompt = McpServerPrompt.Create((MyService actualMyService, int? something = null) =>
        {
            Assert.Same(expectedMyService, actualMyService);
            return new PromptMessage { Role = Role.Assistant, Content = new TextContentBlock { Text = "Hello" } };
        }, new() { Services = services });

        Assert.Contains("something", prompt.ProtocolPrompt.Arguments?.Select(a => a.Name) ?? []);
        Assert.DoesNotContain("actualMyService", prompt.ProtocolPrompt.Arguments?.Select(a => a.Name) ?? []);

        await Assert.ThrowsAnyAsync<ArgumentException>(async () => await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken));

        var result = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object) { Services = services },
            TestContext.Current.CancellationToken);
        Assert.Equal("Hello", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    [Fact]
    public async Task SupportsOptionalServiceFromDI()
    {
        MyService expectedMyService = new();

        ServiceCollection sc = new();
        sc.AddSingleton(expectedMyService);
        IServiceProvider services = sc.BuildServiceProvider();

        McpServerPrompt prompt = McpServerPrompt.Create((MyService? actualMyService = null) =>
        {
            Assert.Null(actualMyService);
            return new PromptMessage { Role = Role.Assistant, Content = new TextContentBlock { Text = "Hello" } };
        }, new() { Services = services });

        var result = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);
        Assert.Equal("Hello", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    [Fact]
    public async Task SupportsDisposingInstantiatedDisposableTargets()
    {
        McpServerPrompt prompt1 = McpServerPrompt.Create(
            typeof(DisposablePromptType).GetMethod(nameof(DisposablePromptType.InstanceMethod))!,
            _ => new DisposablePromptType());

        var result = await prompt1.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);
        Assert.Equal("disposals:1", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    [Fact]
    public async Task SupportsAsyncDisposingInstantiatedAsyncDisposableTargets()
    {
        McpServerPrompt prompt1 = McpServerPrompt.Create(
            typeof(AsyncDisposablePromptType).GetMethod(nameof(AsyncDisposablePromptType.InstanceMethod))!,
            _ => new AsyncDisposablePromptType());

        var result = await prompt1.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);
        Assert.Equal("asyncDisposals:1", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    [Fact]
    public async Task SupportsAsyncDisposingInstantiatedAsyncDisposableAndDisposableTargets()
    {
        McpServerPrompt prompt1 = McpServerPrompt.Create(
            typeof(AsyncDisposableAndDisposablePromptType).GetMethod(nameof(AsyncDisposableAndDisposablePromptType.InstanceMethod))!,
            _ => new AsyncDisposableAndDisposablePromptType());

        var result = await prompt1.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);
        Assert.Equal("disposals:0, asyncDisposals:1", Assert.IsType<TextContentBlock>(result.Messages[0].Content).Text);
    }

    [Fact]
    public async Task CanReturnGetPromptResult()
    {
        GetPromptResult expected = new();

        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return expected;
        });

        var actual = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);

        Assert.Same(expected, actual);
    }

    [Fact]
    public async Task CanReturnText()
    {
        string expected = "hello";

        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return expected;
        });

        var actual = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);

        Assert.NotNull(actual);
        Assert.NotNull(actual.Messages);
        Assert.Single(actual.Messages);
        Assert.Equal(Role.User, actual.Messages[0].Role);
        Assert.Equal("text", actual.Messages[0].Content.Type);
        Assert.Equal(expected, Assert.IsType<TextContentBlock>(actual.Messages[0].Content).Text);
    }

    [Fact]
    public async Task CanReturnPromptMessage()
    {
        PromptMessage expected = new()
        {
            Role = Role.User,
            Content = new TextContentBlock { Text = "hello" }
        };

        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return expected;
        });

        var actual = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);

        Assert.NotNull(actual);
        Assert.NotNull(actual.Messages);
        Assert.Single(actual.Messages);
        Assert.Same(expected, actual.Messages[0]);
    }

    [Fact]
    public async Task CanReturnPromptMessages()
    {
        IList<PromptMessage> expected = 
        [
            new()
            {
                Role = Role.User,
                Content = new TextContentBlock { Text = "hello" }
            },
            new()
            {
                Role = Role.Assistant,
                Content = new TextContentBlock { Text = "hello again" }
            }
        ];

        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return expected;
        });

        var actual = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);

        Assert.NotNull(actual);
        Assert.NotNull(actual.Messages);
        Assert.Equal(2, actual.Messages.Count);
        Assert.Equal(Role.User, actual.Messages[0].Role);
        Assert.Equal("hello", Assert.IsType<TextContentBlock>(actual.Messages[0].Content).Text);
        Assert.Equal(Role.Assistant, actual.Messages[1].Role);
        Assert.Equal("hello again", Assert.IsType<TextContentBlock>(actual.Messages[1].Content).Text);
    }

    [Fact]
    public async Task CanReturnChatMessage()
    {
        PromptMessage expected = new()
        {
            Role = Role.User,
            Content = new TextContentBlock { Text = "hello" }
        };

        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return expected.ToChatMessage();
        });

        var actual = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);

        Assert.NotNull(actual);
        Assert.NotNull(actual.Messages);
        Assert.Single(actual.Messages);
        Assert.Equal(Role.User, actual.Messages[0].Role);
        Assert.Equal("hello", Assert.IsType<TextContentBlock>(actual.Messages[0].Content).Text);
    }

    [Fact]
    public async Task CanReturnChatMessages()
    {
        PromptMessage[] expected = [
            new()
            {
                Role = Role.User,
                Content = new TextContentBlock { Text = "hello" }
            },
            new()
            {
                Role = Role.Assistant,
                Content = new TextContentBlock { Text = "hello again" }
            }
        ];

        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return expected.Select(p => p.ToChatMessage());
        });

        var actual = await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken);

        Assert.NotNull(actual);
        Assert.NotNull(actual.Messages);
        Assert.Equal(2, actual.Messages.Count);
        Assert.Equal(Role.User, actual.Messages[0].Role);
        Assert.Equal("hello", Assert.IsType<TextContentBlock>(actual.Messages[0].Content).Text);
        Assert.Equal(Role.Assistant, actual.Messages[1].Role);
        Assert.Equal("hello again", Assert.IsType<TextContentBlock>(actual.Messages[1].Content).Text);
    }

    [Fact]
    public async Task ThrowsForNullReturn()
    {
        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return (string)null!;
        });

        await Assert.ThrowsAsync<InvalidOperationException>(async () => await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken));
    }

    [Fact]
    public async Task ThrowsForUnexpectedTypeReturn()
    {
        McpServerPrompt prompt = McpServerPrompt.Create(() =>
        {
            return new object();
        });

        await Assert.ThrowsAsync<InvalidOperationException>(async () => await prompt.GetAsync(
            new RequestContext<GetPromptRequestParams>(new Mock<IMcpServer>().Object),
            TestContext.Current.CancellationToken));
    }

    [Fact]
    public async Task SupportsSchemaCreateOptions()
    {
        AIJsonSchemaCreateOptions schemaCreateOptions = new()
        {
            TransformSchemaNode = (context, node) =>
            {
                if (node.GetValueKind() is not JsonValueKind.Object)
                {
                    node = new JsonObject();
                }

                node["description"] = "1234";
                return node;
            }
        };

        McpServerPrompt prompt = McpServerPrompt.Create(([Description("argument1")] int num, [Description("argument2")] string str) =>
        {
            return new ChatMessage(ChatRole.User, "Hello");
        }, new() { SchemaCreateOptions = schemaCreateOptions });

        Assert.NotNull(prompt.ProtocolPrompt.Arguments);
        Assert.All(
            prompt.ProtocolPrompt.Arguments,
            x => Assert.Equal("1234", x.Description)
        );
    }

    private sealed class MyService;

    private class DisposablePromptType : IDisposable
    {
        private readonly ChatMessage _message = new(ChatRole.User, "");

        public int Disposals { get; private set; }

        public void Dispose()
        {
            Disposals++;
            ((TextContent)_message.Contents[0]).Text = $"disposals:{Disposals}";
        }

        public ChatMessage InstanceMethod()
        {
            if (Disposals != 0)
            {
                throw new InvalidOperationException("Dispose was called");
            }

            return _message;
        }
    }

    private class AsyncDisposablePromptType : IAsyncDisposable
    {
        public int AsyncDisposals { get; private set; }
        private ChatMessage _message = new(ChatRole.User, "");

        public ValueTask DisposeAsync()
        {
            AsyncDisposals++;
            ((TextContent)_message.Contents[0]).Text = $"asyncDisposals:{AsyncDisposals}";
            return default;
        }

        public ChatMessage InstanceMethod()
        {
            if (AsyncDisposals != 0)
            {
                throw new InvalidOperationException("DisposeAsync was called");
            }

            return _message;
        }
    }

    private class AsyncDisposableAndDisposablePromptType : IAsyncDisposable, IDisposable
    {
        private readonly ChatMessage _message = new(ChatRole.User, "");

        public int Disposals { get; private set; }
        public int AsyncDisposals { get; private set; }

        public void Dispose()
        {
            Disposals++;
            ((TextContent)_message.Contents[0]).Text = $"disposals:{Disposals}, asyncDisposals:{AsyncDisposals}";
        }

        public ValueTask DisposeAsync()
        {
            AsyncDisposals++;
            ((TextContent)_message.Contents[0]).Text = $"disposals:{Disposals}, asyncDisposals:{AsyncDisposals}";
            return default;
        }

        public ChatMessage InstanceMethod()
        {
            if (Disposals + AsyncDisposals != 0)
            {
                throw new InvalidOperationException("Dispose and/or DisposeAsync was called");
            }

            return _message;
        }
    }
}
