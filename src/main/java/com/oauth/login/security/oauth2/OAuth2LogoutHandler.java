package com.oauth.login.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.oauth.login.exception.BadRequestException;
import com.oauth.login.security.TokenProvider;
import com.oauth.login.util.CookieUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LogoutHandler implements LogoutHandler  /*LogoutHandler*/ {



	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
	
		
	}
}
