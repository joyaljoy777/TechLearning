package com.tech.learning.constants;

import org.springframework.stereotype.Component;

@Component
public class KeyCloakProperties {

    public static final String AUTH_SERVER_URL = "http://localhost:8180/auth";
    public static final String REALM = "SpringBootLearning";
    public static final String CLIENT_ID = "learning-tech-springBoot";
    public static final String USER_ROLE = "user";
    public static final String CLIENT_SECRET = "l4k2mppFEN0WPcl7rFtPaRnNSk8cnYxf";

    public static final String KEYCLOAK_ADMIN = "admin";
    public static final String KEYCLOAK_PASSWORD = "admin";
}
