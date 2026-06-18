package org.booking.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.booking.apigateway.exception.JwtEntryPoint;
import org.booking.apigateway.security.GatewayJwtFilter;
import org.booking.apigateway.security.JwtTokenParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public GatewayJwtFilter authenticationJwtFilter(JwtTokenParser jwtTokenParser, JwtEntryPoint jwtEntryPoint) {
        return new GatewayJwtFilter(jwtTokenParser, jwtEntryPoint);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, GatewayJwtFilter authenticationJwtFilter, JwtEntryPoint jwtEntryPoint) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(jwtEntryPoint)
                )
                .addFilterBefore(authenticationJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    JwtTokenParser jwtTokenParser(
            @Value("${spring.jwt.secret}")
            String secret
    ) {
        return new JwtTokenParser(secret);
    }
}
