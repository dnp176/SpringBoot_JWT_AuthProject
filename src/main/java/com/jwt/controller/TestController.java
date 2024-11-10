package com.jwt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@PostMapping("/demo")
	public String Demo() {
		return "This is demo controller";
	}
	
}
