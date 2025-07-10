﻿using ModelContextProtocol.Protocol;
using ModelContextProtocol.Server;
using System.ComponentModel;

namespace EverythingServer.Tools;

[McpServerToolType]
public class SampleLlmTool
{
    [McpServerTool(Name = "sampleLLM"), Description("Samples from an LLM using MCP's sampling feature")]
    public static async Task<string> SampleLLM(
        IMcpServer server,
        [Description("The prompt to send to the LLM")] string prompt,
        [Description("Maximum number of tokens to generate")] int maxTokens,
        CancellationToken cancellationToken)
    {
        var samplingParams = CreateRequestSamplingParams(prompt ?? string.Empty, "sampleLLM", maxTokens);
        var sampleResult = await server.SampleAsync(samplingParams, cancellationToken);

        return $"LLM sampling result: {(sampleResult.Content as TextContentBlock)?.Text}";
    }

    private static CreateMessageRequestParams CreateRequestSamplingParams(string context, string uri, int maxTokens = 100)
    {
        return new CreateMessageRequestParams
        {
            Messages = [new SamplingMessage
                {
                    Role = Role.User,
                    Content = new TextContentBlock { Text = $"Resource {uri} context: {context}" },
                }],
            SystemPrompt = "You are a helpful test server.",
            MaxTokens = maxTokens,
            Temperature = 0.7f,
            IncludeContext = ContextInclusion.ThisServer
        };
    }
}
