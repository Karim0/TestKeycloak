package com.example.testkeycloak.configuration;

import com.example.testkeycloak.handler.KeycloakLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, proxyTargetClass = true, jsr250Enabled = true)
public class SecurityConfig {

  private final KeycloakLogoutHandler keycloakLogoutHandler;

  SecurityConfig(KeycloakLogoutHandler keycloakLogoutHandler) {
    this.keycloakLogoutHandler = keycloakLogoutHandler;
  }

  @Bean
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().disable()
        .authorizeHttpRequests()
//        .anyRequest()
        .requestMatchers("/customers*")
//        .hasAnyRole("USER", "ADMIN")
//        .anyRequest()
        .authenticated()
        .anyRequest().permitAll();
    http.oauth2Login()
        .and()
        .logout()
        .addLogoutHandler(keycloakLogoutHandler)
        .logoutSuccessUrl("/");
    http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    return http.build();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }
}
