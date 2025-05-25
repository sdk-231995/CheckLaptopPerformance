package com.user.dell;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerFileTestController {
	
	@GetMapping("/dockertest")
	public String getMesssga() {
		return "Docker file testing";
		}

}
