package org.booking.bookingui.config;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/register");
    }

    @Value("${services.api-gateway}")
    private String gateway;

    @Bean("gatewayClient")
    public RestClient gatewayClient() {
        return RestClient.builder()
                .baseUrl(gateway)
                .requestInterceptor(httpInterceptor())
                .build();
    }

    @Bean
    public ClientHttpRequestInterceptor httpInterceptor() {
        return (request, body, execution) -> {

            if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {

                HttpSession session = attrs.getRequest().getSession(false);

                if (session != null) {

                    Object jwt = session.getAttribute("jwt");

                    if (jwt != null && !jwt.toString().isBlank()) {
                        request.getHeaders().setBearerAuth(jwt.toString());
                    }
                }
            }
            return execution.execute(request, body);

        };
    }
}