package com.spring.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

	private final ChatClient chatClient;
	
	public ChatService(@Qualifier("llamaClient") ChatClient chatClient)
	{
		this.chatClient=chatClient;
	}

	public String chat(String message) {
		
		return chatClient.prompt()
				.user(message)
				.call()
				.content();
	}
	
	public  String learn(String role, String topic) {
		
		return chatClient.prompt()
				.user(user -> user.text(" You are a {role}.\r\n"
						+ "                            Explain {topic} in simple language.\r\n"
						+ "                            Give real-world examples.\r\n"
						+ "                            Also provide interview points.").param("role",role)
						.param("topic", topic)).call().content();
	}
}
