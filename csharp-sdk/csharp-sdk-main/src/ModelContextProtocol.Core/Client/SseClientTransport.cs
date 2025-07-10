using Microsoft.Extensions.Logging;
using ModelContextProtocol.Authentication;
using ModelContextProtocol.Protocol;

namespace ModelContextProtocol.Client;

/// <summary>
/// Provides an <see cref="IClientTransport"/> over HTTP using the Server-Sent Events (SSE) or Streamable HTTP protocol.
/// </summary>
/// <remarks>
/// This transport connects to an MCP server over HTTP using SSE or Streamable HTTP,
/// allowing for real-time server-to-client communication with a standard HTTP requests.
/// Unlike the <see cref="StdioClientTransport"/>, this transport connects to an existing server
/// rather than launching a new process.
/// </remarks>
public sealed class SseClientTransport : IClientTransport, IAsyncDisposable
{
    private readonly SseClientTransportOptions _options;
    private readonly McpHttpClient _mcpHttpClient;
    private readonly ILoggerFactory? _loggerFactory;

    private readonly HttpClient? _ownedHttpClient;

    /// <summary>
    /// Initializes a new instance of the <see cref="SseClientTransport"/> class.
    /// </summary>
    /// <param name="transportOptions">Configuration options for the transport.</param>
    /// <param name="loggerFactory">Logger factory for creating loggers used for diagnostic output during transport operations.</param>
    public SseClientTransport(SseClientTransportOptions transportOptions, ILoggerFactory? loggerFactory = null)
        : this(transportOptions, new HttpClient(), loggerFactory, ownsHttpClient: true)
    {
    }

    /// <summary>
    /// Initializes a new instance of the <see cref="SseClientTransport"/> class with a provided HTTP client.
    /// </summary>
    /// <param name="transportOptions">Configuration options for the transport.</param>
    /// <param name="httpClient">The HTTP client instance used for requests.</param>
    /// <param name="loggerFactory">Logger factory for creating loggers used for diagnostic output during transport operations.</param>
    /// <param name="ownsHttpClient">
    /// <see langword="true"/> to dispose of <paramref name="httpClient"/> when the transport is disposed;
    /// <see langword="false"/> if the caller is retaining ownership of the <paramref name="httpClient"/>'s lifetime.
    /// </param>
    public SseClientTransport(SseClientTransportOptions transportOptions, HttpClient httpClient, ILoggerFactory? loggerFactory = null, bool ownsHttpClient = false)
    {
        Throw.IfNull(transportOptions);
        Throw.IfNull(httpClient);

        _options = transportOptions;
        _loggerFactory = loggerFactory;
        Name = transportOptions.Name ?? transportOptions.Endpoint.ToString();

        if (transportOptions.OAuth is { } clientOAuthOptions)
        {
            var oAuthProvider = new ClientOAuthProvider(_options.Endpoint, clientOAuthOptions, httpClient, loggerFactory);
            _mcpHttpClient = new AuthenticatingMcpHttpClient(httpClient, oAuthProvider);
        }
        else
        {
            _mcpHttpClient = new(httpClient);
        }

        if (ownsHttpClient)
        {
            _ownedHttpClient = httpClient;
        }
    }

    /// <inheritdoc />
    public string Name { get; }

    /// <inheritdoc />
    public async Task<ITransport> ConnectAsync(CancellationToken cancellationToken = default)
    {
        return _options.TransportMode switch
        {
            HttpTransportMode.AutoDetect => new AutoDetectingClientSessionTransport(Name, _options, _mcpHttpClient, _loggerFactory),
            HttpTransportMode.StreamableHttp => new StreamableHttpClientSessionTransport(Name, _options, _mcpHttpClient, messageChannel: null, _loggerFactory),
            HttpTransportMode.Sse => await ConnectSseTransportAsync(cancellationToken).ConfigureAwait(false),
            _ => throw new InvalidOperationException($"Unsupported transport mode: {_options.TransportMode}"),
        };
    }

    private async Task<ITransport> ConnectSseTransportAsync(CancellationToken cancellationToken)
    {
        var sessionTransport = new SseClientSessionTransport(Name, _options, _mcpHttpClient, messageChannel: null, _loggerFactory);

        try
        {
            await sessionTransport.ConnectAsync(cancellationToken).ConfigureAwait(false);
            return sessionTransport;
        }
        catch
        {
            await sessionTransport.DisposeAsync().ConfigureAwait(false);
            throw;
        }
    }

    /// <inheritdoc />
    public ValueTask DisposeAsync()
    {
        _ownedHttpClient?.Dispose();
        return default;
    }
}