using ModelContextProtocol.Protocol;
using System.Threading.Channels;

namespace ModelContextProtocol.Server;

/// <summary>
/// Provides an <see cref="ITransport"/> implementation using Server-Sent Events (SSE) for server-to-client communication.
/// </summary>
/// <remarks>
/// <para>
/// This transport provides one-way communication from server to client using the SSE protocol over HTTP,
/// while receiving client messages through a separate mechanism. It writes messages as 
/// SSE events to a response stream, typically associated with an HTTP response.
/// </para>
/// <para>
/// This transport is used in scenarios where the server needs to push messages to the client in real-time,
/// such as when streaming completion results or providing progress updates during long-running operations.
/// </para>
/// </remarks>
/// <param name="sseResponseStream">The response stream to write MCP JSON-RPC messages as SSE events to.</param>
/// <param name="messageEndpoint">
/// The relative or absolute URI the client should use to post MCP JSON-RPC messages for this session.
/// These messages should be passed to <see cref="OnMessageReceivedAsync(JsonRpcMessage, CancellationToken)"/>.
/// Defaults to "/message".
/// </param>
/// <param name="sessionId">The identifier corresponding to the current MCP session.</param>
public sealed class SseResponseStreamTransport(Stream sseResponseStream, string? messageEndpoint = "/message", string? sessionId = null) : ITransport
{
    private readonly SseWriter _sseWriter = new(messageEndpoint);
    private readonly Channel<JsonRpcMessage> _incomingChannel = Channel.CreateBounded<JsonRpcMessage>(new BoundedChannelOptions(1)
    {
        SingleReader = true,
        SingleWriter = false,
    });

    private bool _isConnected;

    /// <summary>
    /// Starts the transport and writes the JSON-RPC messages sent via <see cref="SendMessageAsync"/>
    /// to the SSE response stream until cancellation is requested or the transport is disposed.
    /// </summary>
    /// <param name="cancellationToken">The <see cref="CancellationToken"/> to monitor for cancellation requests. The default is <see cref="CancellationToken.None"/>.</param>
    /// <returns>A task representing the send loop that writes JSON-RPC messages to the SSE response stream.</returns>
    public async Task RunAsync(CancellationToken cancellationToken)
    {
        _isConnected = true;
        await _sseWriter.WriteAllAsync(sseResponseStream, cancellationToken).ConfigureAwait(false);
    }

    /// <inheritdoc/>
    public ChannelReader<JsonRpcMessage> MessageReader => _incomingChannel.Reader;

    /// <inheritdoc/>
    public string? SessionId { get; } = sessionId;

    /// <inheritdoc/>
    public async ValueTask DisposeAsync()
    {
        _isConnected = false;
        _incomingChannel.Writer.TryComplete();
        await _sseWriter.DisposeAsync().ConfigureAwait(false);
    }

    /// <inheritdoc/>
    public async Task SendMessageAsync(JsonRpcMessage message, CancellationToken cancellationToken = default)
    {
        await _sseWriter.SendMessageAsync(message, cancellationToken).ConfigureAwait(false);
    }

    /// <summary>
    /// Handles incoming JSON-RPC messages received on the /message endpoint.
    /// </summary>
    /// <param name="message">The JSON-RPC message received from the client.</param>
    /// <param name="cancellationToken">The <see cref="CancellationToken"/> to monitor for cancellation requests. The default is <see cref="CancellationToken.None"/>.</param>
    /// <returns>A task representing the asynchronous operation to buffer the JSON-RPC message for processing.</returns>
    /// <exception cref="InvalidOperationException">Thrown when there is an attempt to process a message before calling <see cref="RunAsync(CancellationToken)"/>.</exception>
    /// <remarks>
    /// <para>
    /// This method is the entry point for processing client-to-server communication in the SSE transport model. 
    /// While the SSE protocol itself is unidirectional (server to client), this method allows bidirectional 
    /// communication by handling HTTP POST requests sent to the message endpoint.
    /// </para>
    /// <para>
    /// When a client sends a JSON-RPC message to the /message endpoint, the server calls this method to
    /// process the message and make it available to the MCP server via the <see cref="MessageReader"/> channel.
    /// </para>
    /// <para>
    /// This method validates that the transport is connected before processing the message, ensuring proper
    /// sequencing of operations in the transport lifecycle.
    /// </para>
    /// </remarks>
    public async Task OnMessageReceivedAsync(JsonRpcMessage message, CancellationToken cancellationToken)
    {
        Throw.IfNull(message);

        if (!_isConnected)
        {
            throw new InvalidOperationException($"Transport is not connected. Make sure to call {nameof(RunAsync)} first.");
        }

        await _incomingChannel.Writer.WriteAsync(message, cancellationToken).ConfigureAwait(false);
    }
}
