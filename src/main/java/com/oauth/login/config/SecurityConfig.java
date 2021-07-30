package com.oauth.login.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.oauth.login.model.OAuthExtraUrlParameters;
import com.oauth.login.security.RestAuthenticationEntryPoint;
import com.oauth.login.security.TokenAuthenticationFilter;
import com.oauth.login.security.oauth2.CustomOAuth2UserService;
import com.oauth.login.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.oauth.login.security.oauth2.OAuth2AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

//    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    
    @Autowired
    OAuthExtraUrlParameters oAuthExtraUrlParameters;
    
    /*@Autowired
    GenericFilterBean refreshTokenFilter;*/

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
    
    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    /*@Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/auth/**", "/oauth2/**", "/login/**","/profile1")
//                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
//                .authorizationRequestResolver( new CustomAuthorizationRequestResolver2(clientRegistrationRepository(),"/oauth2/authorize-client"))
                .baseUri("/oauth2/authorize")
                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(
                        this.clientRegistrationRepository, oAuthExtraUrlParameters))
//                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                .and()
                .tokenEndpoint()
                .accessTokenResponseClient(accessTokenResponseClient())
                .and()
                .redirectionEndpoint()
//                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .defaultSuccessUrl("/", true)
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)
//                .defaultSuccessUrl("/profile1")
                /*.and()
                .rememberMe()*/;
        
//        http.csrf().disable();
//        http.authorizeRequests().anyRequest().permitAll();

        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(this.refreshTokenFilter,  AnonymousAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
//        accessTokenResponseClient.setRequestEntityConverter(new CustomRequestEntityConverter());
        
        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
        tokenResponseHttpMessageConverter.setTokenResponseConverter(new CustomTokenResponseConverter());
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));
        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        accessTokenResponseClient.setRestOperations(restTemplate);
        return accessTokenResponseClient;
    }
    
   /* @Bean
    public RestTemplate restTemplate() {
       final RestTemplate restTemplate = new RestTemplate();

       List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
       MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
       converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
       messageConverters.add(converter);
       restTemplate.setMessageConverters(messageConverters);

       return restTemplate;
    }*/
    
    /*@Bean
    public OAuthExtraUrlParameters extraUrlParams() {
        return new OAuthExtraUrlParameters();
    }*/
}
