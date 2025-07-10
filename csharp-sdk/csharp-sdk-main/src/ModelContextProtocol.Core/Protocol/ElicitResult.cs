using System.Text.Json;
using System.Text.Json.Serialization;

namespace ModelContextProtocol.Protocol;

/// <summary>
/// Represents the client's response to an elicitation request.
/// </summary>
public sealed class ElicitResult : Result
{
    /// <summary>
    /// Gets or sets the user action in response to the elicitation.
    /// </summary>
    /// <remarks>
    /// <list type="bullet">
    ///   <item>
    ///     <term>"accept"</term>
    ///     <description>User submitted the form/confirmed the action</description>
    ///   </item>
    ///   <item>
    ///     <term>"decline"</term>
    ///     <description>User explicitly declined the action</description>
    ///   </item>
    ///   <item>
    ///     <term>"cancel"</term>
    ///     <description>User dismissed without making an explicit choice</description>
    ///   </item>
    /// </list>
    /// </remarks>
    [JsonPropertyName("action")]
    public string Action { get; set; } = "cancel";

    /// <summary>
    /// Gets or sets the submitted form data.
    /// </summary>
    /// <remarks>
    /// <para>
    /// This is typically omitted if the action is "cancel" or "decline".
    /// </para>
    /// <para>
    /// Values in the dictionary should be of types <see cref="JsonValueKind.String"/>, <see cref="JsonValueKind.Number"/>,
    /// <see cref="JsonValueKind.True"/>, or <see cref="JsonValueKind.False"/>.
    /// </para>
    /// </remarks>
    [JsonPropertyName("content")]
    public IDictionary<string, JsonElement>? Content { get; set; }
}