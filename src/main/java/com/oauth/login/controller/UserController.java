package com.oauth.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oauth.login.model.User;
import com.oauth.login.security.CurrentUser;
import com.oauth.login.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

//@CrossOrigin( origins = "*" )
@RestController
@RequiredArgsConstructor
public class UserController {

	// private final UserRepository userRepository;
	@Autowired
	private OAuth2AuthorizedClientService clientService;

	@GetMapping("/profile")
	// @PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		User u = new User();
		u.setId("1");
		u.setName("Myuser1");
		return u;
		/*
		 * return userRepository.findById(userPrincipal.getId()) .orElseThrow(() -> new
		 * ResourceNotFoundException("User", "id", userPrincipal.getId()));
		 */
	}
	
	@GetMapping("/getuser")
	// @PreAuthorize("hasRole('USER')")
	public User redirectUrl() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
                OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
                client.getAccessToken().getTokenValue();
                client.getRefreshToken();
        }
		
		
		User u = new User();
		u.setId("1");
		u.setName("Myuser1");
		return u;
		/*
		 * return userRepository.findById(userPrincipal.getId()) .orElseThrow(() -> new
		 * ResourceNotFoundException("User", "id", userPrincipal.getId()));
		 */
	}
	
	@GetMapping("/profile1")
	public String redirectUrl(@RequestParam String token) {
		
		return "Your oauth token is = "+token;
	}

	/*@GetMapping("/loginSuccess")
	public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
		
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		OAuth2AuthorizedClient client =
                clientService.loadAuthorizedClient(
                        token.getAuthorizedClientRegistrationId(),
                        token.getName());
		
	    OAuth2AuthorizedClient client = authorizedClientService
	      .loadAuthorizedClient(
	        authentication.getAuthorizedClientRegistrationId(), 
	          authentication.getName());
	    //...
	    return "loginSuccess";
	}*/
}
