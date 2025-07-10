﻿using ModelContextProtocol.Protocol;
using System.Diagnostics;

namespace ModelContextProtocol.Server;

internal sealed class DestinationBoundMcpServer(McpServer server, ITransport? transport) : IMcpServer
{
    public string EndpointName => server.EndpointName;
    public string? SessionId => transport?.SessionId ?? server.SessionId;
    public ClientCapabilities? ClientCapabilities => server.ClientCapabilities;
    public Implementation? ClientInfo => server.ClientInfo;
    public McpServerOptions ServerOptions => server.ServerOptions;
    public IServiceProvider? Services => server.Services;
    public LoggingLevel? LoggingLevel => server.LoggingLevel;

    public ValueTask DisposeAsync() => server.DisposeAsync();

    public IAsyncDisposable RegisterNotificationHandler(string method, Func<JsonRpcNotification, CancellationToken, ValueTask> handler) => server.RegisterNotificationHandler(method, handler);

    // This will throw because the server must already be running for this class to be constructed, but it should give us a good Exception message.
    public Task RunAsync(CancellationToken cancellationToken) => server.RunAsync(cancellationToken);

    public Task SendMessageAsync(JsonRpcMessage message, CancellationToken cancellationToken = default)
    {
        Debug.Assert(message.RelatedTransport is null);
        message.RelatedTransport = transport;
        return server.SendMessageAsync(message, cancellationToken);
    }

    public Task<JsonRpcResponse> SendRequestAsync(JsonRpcRequest request, CancellationToken cancellationToken = default)
    {
        Debug.Assert(request.RelatedTransport is null);
        request.RelatedTransport = transport;
        return server.SendRequestAsync(request, cancellationToken);
    }
}
