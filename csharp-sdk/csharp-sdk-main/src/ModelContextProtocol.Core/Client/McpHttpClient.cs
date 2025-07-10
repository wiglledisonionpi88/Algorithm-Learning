﻿using ModelContextProtocol.Protocol;
using System.Diagnostics;

#if NET
using System.Net.Http.Json;
#else
using System.Text;
using System.Text.Json;
#endif

namespace ModelContextProtocol.Client;

internal class McpHttpClient(HttpClient httpClient)
{
    internal virtual async Task<HttpResponseMessage> SendAsync(HttpRequestMessage request, JsonRpcMessage? message, CancellationToken cancellationToken)
    {
        Debug.Assert(request.Content is null, "The request body should only be supplied as a JsonRpcMessage");
        Debug.Assert(message is null || request.Method == HttpMethod.Post, "All messages should be sent in POST requests.");

        using var content = CreatePostBodyContent(message);
        request.Content = content;
        return await httpClient.SendAsync(request, HttpCompletionOption.ResponseHeadersRead, cancellationToken).ConfigureAwait(false);
    }

    private HttpContent? CreatePostBodyContent(JsonRpcMessage? message)
    {
        if (message is null)
        {
            return null;
        }

#if NET
        return JsonContent.Create(message, McpJsonUtilities.JsonContext.Default.JsonRpcMessage);
#else
        return new StringContent(
            JsonSerializer.Serialize(message, McpJsonUtilities.JsonContext.Default.JsonRpcMessage),
            Encoding.UTF8,
            "application/json"
        );
#endif
    }
}
