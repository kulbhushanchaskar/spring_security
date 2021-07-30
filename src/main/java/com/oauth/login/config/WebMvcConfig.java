package com.oauth.login.config;

import java.io.IOException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final long MAX_AGE_SECS = 3600;

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:3000","http://localhost:8080", "https://www.google.com/")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }*/
    
    @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	 
	    return builder
	    		.errorHandler(new ResponseErrorHandler() {
					
					@Override
					public boolean hasError(ClientHttpResponse response) throws IOException {
						// TODO Auto-generated method stub
						return false;
					}
					
					@Override
					public void handleError(ClientHttpResponse response) throws IOException {
						// TODO Auto-generated method stub
						
					}
				})
	            .build();
	}
}
