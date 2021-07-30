package com.oauth.login.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import com.oauth.login.model.OAuthExtraUrlParameters;

@Component
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
    private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;
    
    OAuthExtraUrlParameters oAuthExtraUrlParameters;

    public CustomAuthorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository, OAuthExtraUrlParameters oAuthExtraUrlParameters) {

        this.defaultAuthorizationRequestResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/oauth2/authorize");
        this.oAuthExtraUrlParameters = oAuthExtraUrlParameters;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
//    	request.r
        OAuth2AuthorizationRequest authorizationRequest =
                this.defaultAuthorizationRequestResolver.resolve(request);

        return authorizationRequest != null ? 
                customAuthorizationRequest(authorizationRequest) :
                null;
    }

    @Override
    public OAuth2AuthorizationRequest resolve(
            HttpServletRequest request, String clientRegistrationId) {

        OAuth2AuthorizationRequest authorizationRequest =
                this.defaultAuthorizationRequestResolver.resolve(
                    request, clientRegistrationId);

        return authorizationRequest != null ?
                customAuthorizationRequest(authorizationRequest) :
                null;
    }

    private OAuth2AuthorizationRequest customAuthorizationRequest(
            OAuth2AuthorizationRequest authorizationRequest) {

        /*Map<String, Object> additionalParameters =
                new LinkedHashMap<>(authorizationRequest.getAdditionalParameters());
        additionalParameters.put("nonce", this.oAuthExtraUrlParameters.getNonce());
        additionalParameters.put("client_type", this.oAuthExtraUrlParameters.getClientType());
        additionalParameters.put("response_type", this.oAuthExtraUrlParameters.getResponseType());*/
        //additionalParameters.put("isInternal", this.oAuthExtraUrlParameters.getIsInternal());

        return OAuth2AuthorizationRequest.from(authorizationRequest)
//                .additionalParameters(additionalParameters)
                .build();
    }
}