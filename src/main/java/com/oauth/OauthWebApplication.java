package com.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.oauth")
public class OauthWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthWebApplication.class, args);
	}

}
