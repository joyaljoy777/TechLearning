package com.tech.learning.configurations;

import com.tech.learning.constants.KeyCloakProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@Configuration
public class KeyCloakConfig {

//    Refactor this into config class and fetch from properties file
//    private static final String AUTH_SERVER_URL = "http://localhost:8180/auth";
//    private static final String REALM = "SpringBootLearning";
//    private static final String CLIENT_ID = "learning-tech-springBoot";
//    private static final String USER_ROLE = "user";
//    private static final String CLIENT_SECRET = "l4k2mppFEN0WPcl7rFtPaRnNSk8cnYxf";
//
//    private static final String KEYCLOAK_ADMIN = "admin";
//    private static final String KEYCLOAK_PASSWORD = "admin";

	@Autowired
	private KeyCloakProperties keyCloakProperties;

	@Bean
	public KeycloakConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean
	public Keycloak getKeycloak() {
		return KeycloakBuilder.builder().serverUrl(keyCloakProperties.AUTH_SERVER_URL)
				.grantType(OAuth2Constants.PASSWORD).realm(keyCloakProperties.REALM)
				.clientId(keyCloakProperties.CLIENT_ID).username(keyCloakProperties.KEYCLOAK_ADMIN)
				.password(keyCloakProperties.KEYCLOAK_PASSWORD)
				.resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
	}
}
