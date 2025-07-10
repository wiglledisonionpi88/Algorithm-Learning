﻿using Microsoft.Extensions.AI;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using ModelContextProtocol.Client;
using ModelContextProtocol.Protocol;
using ModelContextProtocol.Server;
using System.ComponentModel;
using System.Threading.Channels;

namespace ModelContextProtocol.Tests.Configuration;

public partial class McpServerBuilderExtensionsResourcesTests : ClientServerTestBase
{
    public McpServerBuilderExtensionsResourcesTests(ITestOutputHelper testOutputHelper)
        : base(testOutputHelper)
    {
    }

    protected override void ConfigureServices(ServiceCollection services, IMcpServerBuilder mcpServerBuilder)
    {
        mcpServerBuilder
                .WithListResourcesHandler(async (request, cancellationToken) =>
                    {
                        var cursor = request.Params?.Cursor;
                        switch (cursor)
                        {
                            case null:
                                return new()
                                {
                                    NextCursor = "abc",
                                    Resources = [new()
                                    {
                                        Name = "Resource1",
                                        Uri = "test://resource1",
                                    }],
                                };

                            case "abc":
                                return new()
                                {
                                    NextCursor = "def",
                                    Resources = [new()
                                    {
                                        Name = "Resource2",
                                        Uri = "test://resource2",
                                    }],
                                };

                            case "def":
                                return new()
                                {
                                    NextCursor = null,
                                    Resources = [new()
                                    {
                                        Name = "Resource3",
                                        Uri = "test://resource3",
                                    }],
                                };

                            default:
                                throw new McpException($"Unexpected cursor: '{cursor}'", McpErrorCode.InvalidParams);
                        }
                    })
                .WithListResourceTemplatesHandler(async (request, cancellationToken) =>
                    {
                        var cursor = request.Params?.Cursor;
                        switch (cursor)
                        {
                            case null:
                                return new()
                                {
                                    NextCursor = "abc",
                                    ResourceTemplates = [new()
                                    {
                                        Name = "ResourceTemplate1",
                                        UriTemplate = "test://resourceTemplate/{id}",
                                    }],
                                };
                            case "abc":
                                return new()
                                {
                                    NextCursor = null,
                                    ResourceTemplates = [new()
                                    {
                                        Name = "ResourceTemplate2",
                                        UriTemplate = "test://resourceTemplate2/{id}",
                                    }],
                                };
                            default:
                                throw new McpException($"Unexpected cursor: '{cursor}'", McpErrorCode.InvalidParams);
                        }
                    })
        .WithReadResourceHandler(async (request, cancellationToken) =>
        {
            switch (request.Params?.Uri)
            {
                case "test://Resource1":
                case "test://Resource2":
                case "test://Resource3":
                case "test://ResourceTemplate1":
                case "test://ResourceTemplate2":
                    return new ReadResourceResult
                    {
                        Contents = [new TextResourceContents { Text = request.Params?.Uri ?? "(null)" }]
                    };
            }

            throw new McpException($"Resource not found: {request.Params?.Uri}");
        })
        .WithResources<SimpleResources>();
    }

    [Fact]
    public void Adds_Resources_To_Server()
    {
        var serverOptions = ServiceProvider.GetRequiredService<IOptions<McpServerOptions>>().Value;
        var resources = serverOptions?.Capabilities?.Resources?.ResourceCollection;
        Assert.NotNull(resources);
        Assert.NotEmpty(resources);
    }

    [Fact]
    public async Task Can_List_And_Call_Registered_Resources()
    {
        await using IMcpClient client = await CreateMcpClientForServer();

        Assert.NotNull(client.ServerCapabilities.Resources);

        var resources = await client.ListResourcesAsync(TestContext.Current.CancellationToken);
        Assert.Equal(5, resources.Count);

        var resource = resources.First(t => t.Name == "some_neat_direct_resource");
        Assert.Equal("Some neat direct resource", resource.Description);

        var result = await resource.ReadAsync(cancellationToken: TestContext.Current.CancellationToken);
        Assert.NotNull(result);
        Assert.Single(result.Contents);
        Assert.Equal("This is a neat resource", Assert.IsType<TextResourceContents>(result.Contents[0]).Text);
    }

    [Fact]
    public async Task Can_List_And_Call_Registered_ResourceTemplates()
    {
        await using IMcpClient client = await CreateMcpClientForServer();

        var resources = await client.ListResourceTemplatesAsync(TestContext.Current.CancellationToken);
        Assert.Equal(3, resources.Count);

        var resource = resources.First(t => t.Name == "some_neat_templated_resource");
        Assert.Equal("Some neat resource with parameters", resource.Description);

        var result = await resource.ReadAsync(new Dictionary<string, object?>() { ["name"] = "hello" }, cancellationToken: TestContext.Current.CancellationToken);
        Assert.NotNull(result);
        Assert.Single(result.Contents);
        Assert.Equal("This is a neat resource with parameters: hello", Assert.IsType<TextResourceContents>(result.Contents[0]).Text);
    }

    [Fact]
    public async Task Can_Be_Notified_Of_Resource_Changes()
    {
        await using IMcpClient client = await CreateMcpClientForServer();

        var resources = await client.ListResourcesAsync(TestContext.Current.CancellationToken);
        Assert.Equal(5, resources.Count);

        Channel<JsonRpcNotification> listChanged = Channel.CreateUnbounded<JsonRpcNotification>();
        var notificationRead = listChanged.Reader.ReadAsync(TestContext.Current.CancellationToken);
        Assert.False(notificationRead.IsCompleted);

        var serverOptions = ServiceProvider.GetRequiredService<IOptions<McpServerOptions>>().Value;
        var serverResources = serverOptions.Capabilities?.Resources?.ResourceCollection;
        Assert.NotNull(serverResources);

        var newResource = McpServerResource.Create([McpServerResource(Name = "NewResource")] () => "42");
        await using (client.RegisterNotificationHandler("notifications/resources/list_changed", (notification, cancellationToken) =>
            {
                listChanged.Writer.TryWrite(notification);
                return default;
            }))
        {
            serverResources.Add(newResource);
            await notificationRead;

            resources = await client.ListResourcesAsync(TestContext.Current.CancellationToken);
            Assert.Equal(6, resources.Count);
            Assert.Contains(resources, t => t.Name == "NewResource");

            notificationRead = listChanged.Reader.ReadAsync(TestContext.Current.CancellationToken);
            Assert.False(notificationRead.IsCompleted);
            serverResources.Remove(newResource);
            await notificationRead;
        }

        resources = await client.ListResourcesAsync(TestContext.Current.CancellationToken);
        Assert.Equal(5, resources.Count);
        Assert.DoesNotContain(resources, t => t.Name == "NewResource");
    }

    [Fact]
    public async Task TitleAttributeProperty_PropagatedToTitle()
    {
        await using IMcpClient client = await CreateMcpClientForServer();

        var resources = await client.ListResourcesAsync(cancellationToken: TestContext.Current.CancellationToken);
        Assert.NotNull(resources);
        Assert.NotEmpty(resources);
        McpClientResource resource = resources.First(t => t.Name == "some_neat_direct_resource");
        Assert.Equal("This is a title", resource.Title);

        var resourceTemplates = await client.ListResourceTemplatesAsync(cancellationToken: TestContext.Current.CancellationToken);
        Assert.NotNull(resourceTemplates);
        Assert.NotEmpty(resourceTemplates);
        McpClientResourceTemplate resourceTemplate = resourceTemplates.First(t => t.Name == "some_neat_templated_resource");
        Assert.Equal("This is another title", resourceTemplate.Title);
    }

    [Fact]
    public async Task Throws_When_Resource_Fails()
    {
        await using IMcpClient client = await CreateMcpClientForServer();

        await Assert.ThrowsAsync<McpException>(async () => await client.ReadResourceAsync(
            $"resource://mcp/{nameof(SimpleResources.ThrowsException)}",
            cancellationToken: TestContext.Current.CancellationToken));
    }

    [Fact]
    public async Task Throws_Exception_On_Unknown_Resource()
    {
        await using IMcpClient client = await CreateMcpClientForServer();

        var e = await Assert.ThrowsAsync<McpException>(async () => await client.ReadResourceAsync(
            "test:///NotRegisteredResource",
            cancellationToken: TestContext.Current.CancellationToken));

        Assert.Contains("Resource not found", e.Message);
    }

    [Fact]
    public void WithResources_InvalidArgs_Throws()
    {
        IMcpServerBuilder builder = new ServiceCollection().AddMcpServer();

        Assert.Throws<ArgumentNullException>("resourceTemplates", () => builder.WithResources((IEnumerable<McpServerResource>)null!));
        Assert.Throws<ArgumentNullException>("resourceTemplateTypes", () => builder.WithResources((IEnumerable<Type>)null!));

        IMcpServerBuilder nullBuilder = null!;
        Assert.Throws<ArgumentNullException>("builder", () => nullBuilder.WithResources<object>());
        Assert.Throws<ArgumentNullException>("builder", () => nullBuilder.WithResources(Array.Empty<Type>()));
        Assert.Throws<ArgumentNullException>("builder", () => nullBuilder.WithResourcesFromAssembly());
    }

    [Fact]
    public void Empty_Enumerables_Is_Allowed()
    {
        IMcpServerBuilder builder = new ServiceCollection().AddMcpServer();

        builder.WithResources(resourceTemplates: Array.Empty<McpServerResource>()); // no exception
        builder.WithResources(resourceTemplateTypes: Array.Empty<Type>()); // no exception
        builder.WithResources<object>(); // no exception even though no resources exposed
        builder.WithResourcesFromAssembly(typeof(AIFunction).Assembly); // no exception even though no resources exposed
    }

    [Fact]
    public void Register_Resources_From_Current_Assembly()
    {
        ServiceCollection sc = new();
        sc.AddMcpServer().WithResourcesFromAssembly();
        IServiceProvider services = sc.BuildServiceProvider();

        Assert.Contains(services.GetServices<McpServerResource>(), t => t.ProtocolResource?.Uri == $"resource://mcp/some_neat_direct_resource");
        Assert.Contains(services.GetServices<McpServerResource>(), t => t.ProtocolResourceTemplate?.UriTemplate == $"resource://mcp/some_neat_templated_resource{{?name}}");
    }

    [Fact]
    public void Register_Resources_From_Multiple_Sources()
    {
        ServiceCollection sc = new();
        sc.AddMcpServer()
            .WithResources<SimpleResources>()
            .WithResources<MoreResources>()
            .WithResources([McpServerResource.Create(() => "42", new() { UriTemplate = "myResources:///returns42/{something}" })]);
        IServiceProvider services = sc.BuildServiceProvider();

        Assert.Contains(services.GetServices<McpServerResource>(), t => t.ProtocolResource?.Uri == $"resource://mcp/some_neat_direct_resource");
        Assert.Contains(services.GetServices<McpServerResource>(), t => t.ProtocolResourceTemplate?.UriTemplate == $"resource://mcp/some_neat_templated_resource{{?name}}");
        Assert.Contains(services.GetServices<McpServerResource>(), t => t.ProtocolResourceTemplate?.UriTemplate == $"resource://mcp/another_neat_direct_resource");
        Assert.Contains(services.GetServices<McpServerResource>(), t => t.ProtocolResourceTemplate.UriTemplate == "myResources:///returns42/{something}");
    }

    [McpServerResourceType]
    public sealed class SimpleResources
    {
        [McpServerResource(Title = "This is a title"), Description("Some neat direct resource")]
        public static string SomeNeatDirectResource() => "This is a neat resource";

        [McpServerResource(Title = "This is another title"), Description("Some neat resource with parameters")]
        public static string SomeNeatTemplatedResource(string name) => $"This is a neat resource with parameters: {name}";

        [McpServerResource]
        public static string ThrowsException() => throw new InvalidOperationException("uh oh");
    }

    [McpServerResourceType]
    public sealed class MoreResources
    {
        [McpServerResource, Description("Another neat direct resource")]
        public static string AnotherNeatDirectResource() => "This is a neat resource";
    }
}
