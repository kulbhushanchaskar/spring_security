package com.oauth.login.config;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.service.TokenEndpoint;

public class CustomTokenEndpoint extends TokenEndpoint {

public CustomTokenEndpoint(String url, String tokenName) {
		super(url, tokenName);
	}

@RequestMapping(value = "/oauth/login", method=RequestMethod.POST)
public ResponseEntity<OAuth2AccessToken> postAccessToken(Principal principal, @RequestBody Object userLogin) throws HttpRequestMethodNotSupportedException {
	return null;
//        return postAccessToken(principal, userLogin.getParameters());
    }
}
