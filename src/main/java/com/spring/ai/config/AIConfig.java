package com.spring.ai.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

	@Bean("llamaClient")
	public ChatClient llamaClient(OllamaChatModel chatModel) {
		
		return  ChatClient.builder(chatModel)
				.defaultOptions(OllamaOptions.builder()
						.model("llama3:latest")
						.build())
				.build();
	}
	
	@Bean("deepseekClient")
	public ChatClient deepseekClient(OllamaChatModel chatModel) {
		
		return ChatClient.builder(chatModel)
				.defaultOptions(OllamaOptions.builder()
						.model("deepseek-r1:14b")
						.build())
				.build();
	}
	
	@Bean("coderClient")
	public ChatClient CoderClient(OllamaChatModel chatModel) {
		
		return ChatClient.builder(chatModel)
				.defaultOptions(OllamaOptions.builder()
						.model("qwen2.5-coder:7b")
						.build())
				.build();	
	}
}
