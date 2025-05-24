package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
//	public static void main(String[] args) {
//	    for (int port = 8081; port <= 8090; port++) {
//	        new Thread(() -> {
//	        	ConfigurableApplicationContext app = SpringApplication.run(UserServiceApplication.class, args);
//	            ((SpringApplication) app).setDefaultProperties(Map.of("server.port", port));
//	            ((Thread) app).run();
//	        }).start();
//	    }
//	}


}
