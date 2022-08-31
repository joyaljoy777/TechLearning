package com.tech.learning.configurations;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
class KeyCloakSpringSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }
//
//    @Bean
//    @Override
//    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//        return new RegisterSessionAuthenticationStrategy(
//                new SessionRegistryImpl());
//    }
//
////    @Bean
////    public KeycloakConfigResolver KeycloakConfigResolver() {
////        return new KeycloakSpringBootConfigResolver();
////    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/user*")
//                .hasRole("user")
//                .anyRequest()
//                .permitAll();
//    }



//    /**
//     * Registers the KeycloakAuthenticationProvider with the authentication manager.
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(keycloakAuthenticationProvider());
//    }

    /**
     * Defines the session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(buildSessionRegistry());
    }

    @Bean
    protected SessionRegistry buildSessionRegistry() {
        return new SessionRegistryImpl();
    }

//    This needs to be moved to some other config class...why? circular dependency
//    @Bean
//    public KeycloakConfigResolver KeycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/user*").hasRole("user")
//                .anyRequest().permitAll();

//        http.httpBasic().and()
//                .authorizeRequests()
//                .antMatchers("/user/*").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable();

//        super.configure(http);
//        http.cors().and()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/user/*").hasAnyRole("user", "admin")
////                .antMatchers("/user/getAllUsers").hasRole("admin")
//                .antMatchers("/user/signin").permitAll()
////                .antMatchers("/user/*").permitAll()
//                .antMatchers("/user/*").hasRole("user")
//                .anyRequest().authenticated();

        super.configure(http);
        http.cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/getAllUsers").hasRole("ADMIN")
                .antMatchers("/user/*").permitAll()
                .antMatchers("/**").permitAll();

    }
}