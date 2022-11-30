package com.mapfiltermagic.startintermediary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {                       
        http.csrf().and().cors().disable();

        return http.build();
    }

}
