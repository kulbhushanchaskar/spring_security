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
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler /*AuthenticationSuccessHandler*/ {

    private final TokenProvider tokenProvider;

    private OAuth2AuthorizedClientRepository authorizedClientRepository;

//    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    
    @Autowired
	private OAuth2AuthorizedClientService clientService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
                OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
                client.getAccessToken().getTokenValue();
                client.getRefreshToken();
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        /*Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }*/

        String targetUrl = "http://localhost:8080/profile1";//redirectUri.orElse(getDefaultTargetUrl());

        String token = tokenProvider.createToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
//        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
       
        return true;
    }
}
