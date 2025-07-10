# typed: strict
# frozen_string_literal: true

module MCP
  class Resource
    attr_reader :uri, :name, :description, :mime_type

    def initialize(uri:, name:, description: nil, mime_type: nil)
      @uri = uri
      @name = name
      @description = description
      @mime_type = mime_type
    end

    def to_h
      {
        uri: @uri,
        name: @name,
        description: @description,
        mimeType: @mime_type,
      }.compact
    end
  end
end
