package com.oauth.login.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("spring.security.oauth2.client.registration.luminate")
public class OAuthExtraUrlParameters {
	
	String nonce;
	String clientType;
	String responseType;
	String isInternal;
	String PA;
}
