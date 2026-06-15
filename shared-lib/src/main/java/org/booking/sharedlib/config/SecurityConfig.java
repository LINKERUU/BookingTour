package org.booking.sharedlib.config;

import org.booking.sharedlib.security.AuthFilter;
import org.booking.sharedlib.security.JwtTokenParser;
import org.booking.sharedlib.security.exception.CustomAccessDeniedHandler;
import org.booking.sharedlib.security.exception.CustomEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@AutoConfiguration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public CustomEntryPoint customEntryPoint(ObjectMapper objectMapper) {
        return new CustomEntryPoint(objectMapper);
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(ObjectMapper objectMapper) {
        return new CustomAccessDeniedHandler(objectMapper);
    }

    @Bean
    public JwtTokenParser jwtTokenParser(
            @Value("${spring.jwt.secret}") String jwtSecret
    ) {
        return new JwtTokenParser(jwtSecret);
    }

    @Bean
    public AuthFilter authFilter(JwtTokenParser parser) {
        return new AuthFilter(parser);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenParser parser, CustomEntryPoint entryPoint,
                                                   CustomAccessDeniedHandler accessDeniedHandler) {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(authFilter(parser), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
