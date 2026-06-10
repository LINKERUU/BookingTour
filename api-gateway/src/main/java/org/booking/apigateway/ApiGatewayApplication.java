package org.booking.apigateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ApiGatewayApplication {
    static void main(String[] args) {
        new SpringApplicationBuilder(ApiGatewayApplication.class)
                .run(args);
    }
}