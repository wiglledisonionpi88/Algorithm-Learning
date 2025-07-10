using System.ComponentModel;
using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace ModelContextProtocol.Protocol;

/// <summary>
/// Represents a progress token, which can be either a string or an integer.
/// </summary>
[JsonConverter(typeof(Converter))]
public readonly struct ProgressToken : IEquatable<ProgressToken>
{
    /// <summary>The token, either a string or a boxed long or null.</summary>
    private readonly object? _token;

    /// <summary>Initializes a new instance of the <see cref="ProgressToken"/> with a specified value.</summary>
    /// <param name="value">The required ID value.</param>
    public ProgressToken(string value)
    {
        Throw.IfNull(value);
        _token = value;
    }

    /// <summary>Initializes a new instance of the <see cref="ProgressToken"/> with a specified value.</summary>
    /// <param name="value">The required ID value.</param>
    public ProgressToken(long value)
    {
        // Box the long. Progress tokens are almost always strings in practice, so this should be rare.
        _token = value;
    }

    /// <summary>Gets the underlying object for this token.</summary>
    /// <remarks>This will either be a <see cref="string"/>, a boxed <see cref="long"/>, or <see langword="null"/>.</remarks>
    public object? Token => _token;

    /// <inheritdoc />
    public override string? ToString() =>
        _token is string stringValue ? stringValue :
        _token is long longValue ? longValue.ToString(CultureInfo.InvariantCulture) :
        null;

    /// <inheritdoc />
    public bool Equals(ProgressToken other) => Equals(_token, other._token);

    /// <inheritdoc />
    public override bool Equals(object? obj) => obj is ProgressToken other && Equals(other);

    /// <inheritdoc />
    public override int GetHashCode() => _token?.GetHashCode() ?? 0;

    /// <inheritdoc />
    public static bool operator ==(ProgressToken left, ProgressToken right) => left.Equals(right);

    /// <inheritdoc />
    public static bool operator !=(ProgressToken left, ProgressToken right) => !left.Equals(right);

    /// <summary>
    /// Provides a <see cref="JsonConverter"/> for <see cref="ProgressToken"/> that handles both string and number values.
    /// </summary>
    [EditorBrowsable(EditorBrowsableState.Never)]
    public sealed class Converter : JsonConverter<ProgressToken>
    {
        /// <inheritdoc />
        public override ProgressToken Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
        {
            return reader.TokenType switch
            {
                JsonTokenType.String => new(reader.GetString()!),
                JsonTokenType.Number => new(reader.GetInt64()),
                _ => throw new JsonException("progressToken must be a string or an integer"),
            };
        }

        /// <inheritdoc />
        public override void Write(Utf8JsonWriter writer, ProgressToken value, JsonSerializerOptions options)
        {
            Throw.IfNull(writer);

            switch (value._token)
            {
                case string str:
                    writer.WriteStringValue(str);
                    return;

                case long longValue:
                    writer.WriteNumberValue(longValue);
                    return;

                case null:
                    writer.WriteStringValue(string.Empty);
                    return;
            }
        }
    }
}