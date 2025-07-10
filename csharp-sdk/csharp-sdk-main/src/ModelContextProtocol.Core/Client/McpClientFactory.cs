using Microsoft.Extensions.Logging;

namespace ModelContextProtocol.Client;

/// <summary>
/// Provides factory methods for creating Model Context Protocol (MCP) clients.
/// </summary>
/// <remarks>
/// This factory class is the primary way to instantiate <see cref="IMcpClient"/> instances
/// that connect to MCP servers. It handles the creation and connection
/// of appropriate implementations through the supplied transport.
/// </remarks>
public static partial class McpClientFactory
{
    /// <summary>Creates an <see cref="IMcpClient"/>, connecting it to the specified server.</summary>
    /// <param name="clientTransport">The transport instance used to communicate with the server.</param>
    /// <param name="clientOptions">
    /// A client configuration object which specifies client capabilities and protocol version.
    /// If <see langword="null"/>, details based on the current process will be employed.
    /// </param>
    /// <param name="loggerFactory">A logger factory for creating loggers for clients.</param>
    /// <param name="cancellationToken">The <see cref="CancellationToken"/> to monitor for cancellation requests. The default is <see cref="CancellationToken.None"/>.</param>
    /// <returns>An <see cref="IMcpClient"/> that's connected to the specified server.</returns>
    /// <exception cref="ArgumentNullException"><paramref name="clientTransport"/> is <see langword="null"/>.</exception>
    /// <exception cref="ArgumentNullException"><paramref name="clientOptions"/> is <see langword="null"/>.</exception>
    public static async Task<IMcpClient> CreateAsync(
        IClientTransport clientTransport,
        McpClientOptions? clientOptions = null,
        ILoggerFactory? loggerFactory = null,
        CancellationToken cancellationToken = default)
    {
        Throw.IfNull(clientTransport);

        McpClient client = new(clientTransport, clientOptions, loggerFactory);
        try
        {
            await client.ConnectAsync(cancellationToken).ConfigureAwait(false);
            if (loggerFactory?.CreateLogger(typeof(McpClientFactory)) is ILogger logger)
            {
                logger.LogClientCreated(client.EndpointName);
            }
        }
        catch
        {
            await client.DisposeAsync().ConfigureAwait(false);
            throw;
        }

        return client;
    }

    [LoggerMessage(Level = LogLevel.Information, Message = "{EndpointName} client created and connected.")]
    private static partial void LogClientCreated(this ILogger logger, string endpointName);
}