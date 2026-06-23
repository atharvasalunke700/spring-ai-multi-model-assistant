package com.spring.ai.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("api/chat")
public class ChatController {

	private final ChatClient llamaClient ;
	
	private final ChatClient deepseekClient;
	
	private final ChatClient coderClient;

	public ChatController(
			@Qualifier("llamaClient")ChatClient llamaClient,
			@Qualifier("deepseekClient") ChatClient deepseekClient,
			@Qualifier("coderClient")ChatClient coderClient) {
		 
		this.llamaClient=llamaClient;
		this.deepseekClient=deepseekClient;
		this.coderClient=coderClient;
	}
	
	@GetMapping("/llama")
	public ResponseEntity<String>chatWithLlama(@RequestParam String message){
		 
		String response =llamaClient
				.prompt()
				.user(message)
				.call()
				.content();
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/deepseek")
	public ResponseEntity<String>ChatWithDeepseek(@RequestParam String message)
	{
		 String response=deepseekClient
				 .prompt()
				 .user(message)
				 .call()
				 .content();
		 
		 return ResponseEntity.ok(response);
	}
	
	@GetMapping("/coder")
	public ResponseEntity<String>ChatWithCoder(@RequestParam String message)
	{
		String response=coderClient.prompt()
				.user(message)
				.call()
				.content();
		
		return ResponseEntity.ok(response);
	}
	
	
}
