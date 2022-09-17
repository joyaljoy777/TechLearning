package com.tech.learning.controllers;

import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/msg")
	public ResponseEntity<String> getAllUsers() {
		
		return ResponseEntity.ok("Test Message from spring boot application - TechLearning");
	}

}
