package com.tech.learning.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@GetMapping("/test/msg")
	public ResponseEntity<String> getAllUsers() {
		
		return ResponseEntity.ok("Test Message from spring boot application - TechLearning");
	}

	@RequestMapping("/")
	@ResponseBody
	public String helloWorld(){
		return "Hello World From GKE Via Github Actions!";
	}

}
