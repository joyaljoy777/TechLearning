package com.tech.learning.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/msg")
	public ResponseEntity<String> getAllUsers() {
		
		return ResponseEntity.ok("Test Message from spring boot application - TechLearning");
	}

}
