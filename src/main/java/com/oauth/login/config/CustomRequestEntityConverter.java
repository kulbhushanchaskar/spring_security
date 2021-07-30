package com.oauth.login.config;
/*package com.walmart.login.config;

import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;

public class CustomRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;
    
    public CustomRequestEntityConverter() {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }
    
    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {
    	
//    	ClientRegistration clientRegistration = req.getClientRegistration();
//		HttpHeaders headers = clientRegistration.
    	
    	
        RequestEntity<?> entity = defaultConverter.convert(req);
        
        HttpHeaders headers = entity.getHeaders();
        headers.set("Authorization", headers.getFirst("Authorization"));
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        
//        HttpHeaders headersModified = new HttpHeaders().writableHttpHeaders(headers);
        HttpHeaders mutableHttpHeaders = new HttpHeaders(headers);
        ArrayList<String> a = new ArrayList<>();
        a.add("application/json");
        mutableHttpHeaders.put("Content-Type", a);
        
        HttpHeaders headersModified = new HttpHeaders();
        
        headersModified.set("Authorization", headers.getFirst("Authorization"));
//        headersModified.set("Content-Type", "application/json");
//        headersModified.set("Accept", "application/json;charset=UTF-8");
        
        headersModified.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headersModified.setContentType(MediaType.APPLICATION_JSON);
        
//        MultiValueMap<String, String> params = (MultiValueMap<String,String>) entity.getBody();
//        params.add("test2", "extra2");
//        System.out.println(params.entrySet());
        return new RequestEntity<>(entity.getBody(), headersModified, entity.getMethod(), entity.getUrl());
    }

}
*/