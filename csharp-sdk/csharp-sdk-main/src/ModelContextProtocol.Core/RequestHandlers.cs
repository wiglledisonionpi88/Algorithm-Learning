using ModelContextProtocol.Protocol;
using System.Text.Json;
using System.Text.Json.Nodes;
using System.Text.Json.Serialization.Metadata;

namespace ModelContextProtocol;

internal sealed class RequestHandlers : Dictionary<string, Func<JsonRpcRequest, CancellationToken, Task<JsonNode?>>>
{
    /// <summary>
    /// Registers a handler for incoming requests of a specific method in the MCP protocol.
    /// </summary>
    /// <typeparam name="TRequest">Type of request payload that will be deserialized from incoming JSON</typeparam>
    /// <typeparam name="TResponse">Type of response payload that will be serialized to JSON (not full RPC response)</typeparam>
    /// <param name="method">Method identifier to register for (e.g., "tools/list", "logging/setLevel")</param>
    /// <param name="handler">Handler function to be called when a request with the specified method identifier is received</param>
    /// <param name="requestTypeInfo">The JSON contract governing request parameter deserialization</param>
    /// <param name="responseTypeInfo">The JSON contract governing response serialization</param>
    /// <remarks>
    /// <para>
    /// This method is used internally by the MCP infrastructure to register handlers for various protocol methods.
    /// When an incoming request matches the specified method, the registered handler will be invoked with the
    /// deserialized request parameters.
    /// </para>
    /// <para>
    /// The handler function receives the deserialized request object and a cancellation token, and should return
    /// a response object that will be serialized back to the client.
    /// </para>
    /// </remarks>
    public void Set<TRequest, TResponse>(
        string method,
        Func<TRequest?, ITransport?, CancellationToken, ValueTask<TResponse>> handler,
        JsonTypeInfo<TRequest> requestTypeInfo,
        JsonTypeInfo<TResponse> responseTypeInfo)
    {
        Throw.IfNull(method);
        Throw.IfNull(handler);
        Throw.IfNull(requestTypeInfo);
        Throw.IfNull(responseTypeInfo);

        this[method] = async (request, cancellationToken) =>
        {
            TRequest? typedRequest = JsonSerializer.Deserialize(request.Params, requestTypeInfo);
            object? result = await handler(typedRequest, request.RelatedTransport, cancellationToken).ConfigureAwait(false);
            return JsonSerializer.SerializeToNode(result, responseTypeInfo);
        };
    }
}
