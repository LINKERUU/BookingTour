package org.booking.orderservice;

import org.booking.sharedlib.messaging.config.SharedRabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SharedRabbitMQConfig.class)
public class OrderServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
