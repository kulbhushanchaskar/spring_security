package com.oauth.login.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("spring.security.token")
public class TokenProperties {
	
	String tokenExpirationMsec;
	String tokenSecret;
}
