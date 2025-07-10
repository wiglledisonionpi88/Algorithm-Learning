using ModelContextProtocol.Protocol;

namespace ModelContextProtocol.Client;

/// <summary>
/// Represents a named resource template that can be retrieved from an MCP server.
/// </summary>
/// <remarks>
/// <para>
/// This class provides a client-side wrapper around a resource template defined on an MCP server. It allows
/// retrieving the resource template's content by sending a request to the server with the resource's URI.
/// Instances of this class are typically obtained by calling <see cref="McpClientExtensions.ListResourceTemplatesAsync"/>
/// or <see cref="McpClientExtensions.EnumerateResourceTemplatesAsync"/>.
/// </para>
/// </remarks>
public sealed class McpClientResourceTemplate
{
    private readonly IMcpClient _client;

    internal McpClientResourceTemplate(IMcpClient client, ResourceTemplate resourceTemplate)
    {
        _client = client;
        ProtocolResourceTemplate = resourceTemplate;
    }

    /// <summary>Gets the underlying protocol <see cref="ResourceTemplate"/> type for this instance.</summary>
    /// <remarks>
    /// <para>
    /// This property provides direct access to the underlying protocol representation of the resource template,
    /// which can be useful for advanced scenarios or when implementing custom MCP client extensions.
    /// </para>
    /// <para>
    /// For most common use cases, you can use the more convenient <see cref="UriTemplate"/> and 
    /// <see cref="Description"/> properties instead of accessing the <see cref="ProtocolResourceTemplate"/> directly.
    /// </para>
    /// </remarks>
    public ResourceTemplate ProtocolResourceTemplate { get; }

    /// <summary>Gets the URI template of the resource template.</summary>
    public string UriTemplate => ProtocolResourceTemplate.UriTemplate;

    /// <summary>Gets the name of the resource template.</summary>
    public string Name => ProtocolResourceTemplate.Name;

    /// <summary>Gets the title of the resource template.</summary>
    public string? Title => ProtocolResourceTemplate.Title;

    /// <summary>Gets a description of the resource template.</summary>
    public string? Description => ProtocolResourceTemplate.Description;

    /// <summary>Gets a media (MIME) type of the resource template.</summary>
    public string? MimeType => ProtocolResourceTemplate.MimeType;

    /// <summary>
    /// Gets this resource template's content by formatting a URI from the template and supplied arguments
    /// and sending a request to the server.
    /// </summary>
    /// <param name="arguments">A dictionary of arguments to pass to the tool. Each key represents a parameter name,
    /// and its associated value represents the argument value.
    /// </param>
    /// <param name="cancellationToken">The <see cref="CancellationToken"/> to monitor for cancellation requests. The default is <see cref="CancellationToken.None"/>.</param>
    /// <returns>A <see cref="ValueTask{ReadResourceResult}"/> containing the resource template's result with content and messages.</returns>
    public ValueTask<ReadResourceResult> ReadAsync(
        IReadOnlyDictionary<string, object?> arguments,
        CancellationToken cancellationToken = default) =>
        _client.ReadResourceAsync(UriTemplate, arguments, cancellationToken);
}