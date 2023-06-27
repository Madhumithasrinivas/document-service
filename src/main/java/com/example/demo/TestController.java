package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	
	@GetMapping("/USERS")
	public String getName() {
		return "TEST CLASS IS WORKING";
	}

}
