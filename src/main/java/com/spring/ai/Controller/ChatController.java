package com.spring.ai.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.ai.dto.TeacherRequest;
import com.spring.ai.service.ChatService;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
	
	private final ChatService  chatService;
	
	private final ChatClient llamaClient ;
	
	private final ChatClient deepseekClient;
	
	private final ChatClient coderClient;

	public ChatController( ChatService chatService,
			@Qualifier("llamaClient")ChatClient llamaClient,
			@Qualifier("deepseekClient") ChatClient deepseekClient,
			@Qualifier("coderClient")ChatClient coderClient) {
		 
		this.chatService=chatService;
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
	
	//system prompt 
	@GetMapping("/java-teacher")
	public ResponseEntity<String>javaTeacher(@RequestParam String message )
	{
		String response=llamaClient.prompt()
				.system("""
	                    You are an expert Java/python teacher.
	                    Explain concepts in simple language.
	                   Give examples and code snippets.
	                    Teach step by step.
	                    """)
				.user(message)
				.call()
				.content();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/teacher")
	public ResponseEntity<String> teacher(
	        @RequestBody TeacherRequest request) {

	    String prompt = """
	            You are a %s.

	            Your audience level is %s.

	            Explain the topic %s in simple language.

	            Give real-world examples and Java code examples if needed.

	            Explain step by step.
	            """
	            .formatted(
	                    request.getRole(),
	                    request.getLevel(),
	                    request.getTopic()
	            );

	    String response = llamaClient
	            .prompt()
	            .user(prompt)
	            .call()
	            .content();

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/learn")
	public String learn(@RequestParam String role, @RequestParam String topic) {
	
		return chatService.learn(role, topic) ;
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
