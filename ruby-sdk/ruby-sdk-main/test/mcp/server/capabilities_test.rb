# frozen_string_literal: true

require "test_helper"

module MCP
  class Server
    class CapabilitiesTest < ActiveSupport::TestCase
      test "can be initialized with a capabilities hash" do
        capabilities = MCP::Server::Capabilities.new(prompts: {})

        assert_equal({ prompts: {} }, capabilities.to_h)
      end

      test "ignores unknown values in capabilities hash" do
        capabilities = MCP::Server::Capabilities.new(prompts: {}, invalid: :value)

        assert_equal({ prompts: {} }, capabilities.to_h)
      end

      test "support prompts" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_prompts

        assert_operator capabilities.to_h, :key?, :prompts
      end

      test "support resources" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_resources

        assert_operator capabilities.to_h, :key?, :resources
      end

      test "support tools" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_tools

        assert_operator capabilities.to_h, :key?, :tools
      end

      test "support completions" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_completions

        assert_operator capabilities.to_h, :key?, :completions
        assert_empty(capabilities.to_h[:completions])
      end

      test "support logging" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_logging

        assert_operator capabilities.to_h, :key?, :logging
        assert_empty(capabilities.to_h[:logging])
      end

      test "support experimental with custom config" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_experimental({ myFeature: { enabled: true } })

        assert_operator capabilities.to_h, :key?, :experimental
        assert_equal({ myFeature: { enabled: true } }, capabilities.to_h[:experimental])
      end

      test "support prompts list changed" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_prompts_list_changed

        assert_equal({ prompts: { listChanged: true } }, capabilities.to_h)
      end

      test "support resources list changed" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_resources_list_changed

        assert_equal({ resources: { listChanged: true } }, capabilities.to_h)
      end

      test "support resources subscribe" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_resources_subscribe

        assert_equal({ resources: { subscribe: true } }, capabilities.to_h)
      end

      test "support resources with both list changed and subscribe" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_resources_list_changed
        capabilities.support_resources_subscribe

        assert_equal({ resources: { listChanged: true, subscribe: true } }, capabilities.to_h)
      end

      test "support tools list changed" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_tools_list_changed

        assert_equal({ tools: { listChanged: true } }, capabilities.to_h)
      end

      test "initializes with prompts list changed from hash" do
        capabilities = MCP::Server::Capabilities.new(prompts: { listChanged: true })

        assert_equal({ prompts: { listChanged: true } }, capabilities.to_h)
      end

      test "initializes with resources list changed and subscribe from hash" do
        capabilities = MCP::Server::Capabilities.new(resources: { listChanged: true, subscribe: true })

        assert_equal({ resources: { listChanged: true, subscribe: true } }, capabilities.to_h)
      end

      test "initializes with tools list changed from hash" do
        capabilities = MCP::Server::Capabilities.new(tools: { listChanged: true })

        assert_equal({ tools: { listChanged: true } }, capabilities.to_h)
      end

      test "initializes with completions from hash" do
        capabilities = MCP::Server::Capabilities.new(completions: {})

        assert_equal({ completions: {} }, capabilities.to_h)
      end

      test "initializes with logging from hash" do
        capabilities = MCP::Server::Capabilities.new(logging: {})

        assert_equal({ logging: {} }, capabilities.to_h)
      end

      test "initializes with experimental config from hash" do
        capabilities = MCP::Server::Capabilities.new(experimental: { feature1: { enabled: true }, feature2: "config" })

        assert_equal({ experimental: { feature1: { enabled: true }, feature2: "config" } }, capabilities.to_h)
      end

      test "initializes with all capabilities from hash" do
        capabilities = MCP::Server::Capabilities.new(
          completions: {},
          experimental: { customFeature: true },
          logging: {},
          prompts: { listChanged: true },
          resources: { listChanged: true, subscribe: true },
          tools: { listChanged: true },
        )

        expected = {
          completions: {},
          experimental: { customFeature: true },
          logging: {},
          prompts: { listChanged: true },
          resources: { listChanged: true, subscribe: true },
          tools: { listChanged: true },
        }

        assert_equal(expected, capabilities.to_h)
      end

      test "handles nil values in capabilities hash gracefully" do
        capabilities = MCP::Server::Capabilities.new(
          prompts: nil,
          resources: nil,
          tools: nil,
        )

        assert_equal({ prompts: {}, resources: {}, tools: {} }, capabilities.to_h)
      end

      test "to_h returns empty hash when no capabilities are set" do
        capabilities = MCP::Server::Capabilities.new

        assert_empty(capabilities.to_h)
      end

      test "calling support methods multiple times does not duplicate entries" do
        capabilities = MCP::Server::Capabilities.new

        capabilities.support_prompts
        capabilities.support_prompts
        capabilities.support_prompts_list_changed

        assert_equal({ prompts: { listChanged: true } }, capabilities.to_h)
      end
    end
  end
end
