package com.in28minutes.rest.webservices.restfulwebservices.securities;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // all requests must be authenticated
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );

        //if a request is not authenticated, a web page is shown
        http.httpBasic(Customizer.withDefaults());

        //csrf -> POST, PUT
        http.csrf().disable();
        return http.build();

    }
}
