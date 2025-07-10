# typed: strict
# frozen_string_literal: true

require "test_helper"

module MCP
  class PromptTest < ActiveSupport::TestCase
    class TestPrompt < Prompt
      description "Test prompt"
      arguments [
        Prompt::Argument.new(name: "test_argument", description: "Test argument", required: true),
      ]

      class << self
        def template(args, server_context:)
          Prompt::Result.new(
            description: "Hello, world!",
            messages: [
              Prompt::Message.new(role: "user", content: Content::Text.new("Hello, world!")),
              Prompt::Message.new(role: "assistant", content: Content::Text.new("Hello, friend!")),
            ],
          )
        end
      end
    end

    test "#template returns a Result with description and messages" do
      prompt = TestPrompt

      expected_template_result = {
        description: "Hello, world!",
        messages: [
          { role: "user", content: { text: "Hello, world!", type: "text" } },
          { role: "assistant", content: { text: "Hello, friend!", type: "text" } },
        ],
      }

      result = prompt.template({ "test_argument" => "Hello, friend!" }, server_context: { user_id: 123 })

      assert_equal expected_template_result, result.to_h
    end

    test "allows declarative definition of prompts as classes" do
      class MockPrompt < Prompt
        prompt_name "my_mock_prompt"
        description "a mock prompt for testing"
        arguments [
          Prompt::Argument.new(name: "test_argument", description: "Test argument", required: true),
        ]

        class << self
          def template(args, server_context:)
            Prompt::Result.new(
              description: "Hello, world!",
              messages: [
                Prompt::Message.new(role: "user", content: Content::Text.new("Hello, world!")),
                Prompt::Message.new(role: "assistant", content: Content::Text.new(args["test_argument"])),
              ],
            )
          end
        end
      end

      prompt = MockPrompt

      assert_equal "my_mock_prompt", prompt.name_value
      assert_equal "a mock prompt for testing", prompt.description
      assert_equal "test_argument", prompt.arguments.first.name
      assert_equal "Test argument", prompt.arguments.first.description
      assert prompt.arguments.first.required

      expected_template_result = {
        description: "Hello, world!",
        messages: [
          { role: "user", content: { text: "Hello, world!", type: "text" } },
          { role: "assistant", content: { text: "Hello, friend!", type: "text" } },
        ],
      }

      result = prompt.template({ "test_argument" => "Hello, friend!" }, server_context: { user_id: 123 })
      assert_equal expected_template_result, result.to_h
    end

    test "defaults to class name as prompt name" do
      class DefaultNamePrompt < Prompt
        description "a mock prompt for testing"
        arguments [
          Prompt::Argument.new(name: "test_argument", description: "Test argument", required: true),
        ]

        class << self
          def template(args, server_context:)
            Prompt::Result.new(
              description: "Hello, world!",
              messages: [
                Prompt::Message.new(role: "user", content: Content::Text.new("Hello, world!")),
                Prompt::Message.new(role: "assistant", content: Content::Text.new(args["test_argument"])),
              ],
            )
          end
        end
      end

      prompt = DefaultNamePrompt

      assert_equal "default_name_prompt", prompt.name_value
      assert_equal "a mock prompt for testing", prompt.description
      assert_equal "test_argument", prompt.arguments.first.name
    end

    test ".define allows definition of simple prompts with a block" do
      prompt = Prompt.define(
        name: "mock_prompt",
        description: "a mock prompt for testing",
        arguments: [
          Prompt::Argument.new(name: "test_argument", description: "Test argument", required: true),
        ],
      ) do |args, server_context:|
        content = Content::Text.new(args["test_argument"] + " user: #{server_context[:user_id]}")

        Prompt::Result.new(
          description: "Hello, world!",
          messages: [
            Prompt::Message.new(role: "user", content: Content::Text.new("Hello, world!")),
            Prompt::Message.new(role: "assistant", content:),
          ],
        )
      end

      assert_equal "mock_prompt", prompt.name_value
      assert_equal "a mock prompt for testing", prompt.description
      assert_equal "test_argument", prompt.arguments.first.name

      expected = {
        description: "Hello, world!",
        messages: [
          { role: "user", content: { text: "Hello, world!", type: "text" } },
          { role: "assistant", content: { text: "Hello, friend! user: 123", type: "text" } },
        ],
      }

      result = prompt.template({ "test_argument" => "Hello, friend!" }, server_context: { user_id: 123 })
      assert_equal expected, result.to_h
    end
  end
end
