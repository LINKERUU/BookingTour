package org.booking.sharedlib.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.booking.sharedlib.config.RabbitMQConstants.*;

@Configuration
public class SharedRabbitMQConfig {

    @Bean
    Queue flightCancelQueue() {
        return QueueBuilder.durable(FLIGHT_CANCEL_QUEUE).build();
    }

    @Bean
    Binding flightCancelBinding() {
        return BindingBuilder.bind(flightCancelQueue())
                .to(bookingExchange()).with(FLIGHT_CANCEL_KEY);
    }

    @Bean
    Queue hotelCancelQueue() {
        return QueueBuilder.durable(HOTEL_CANCEL_QUEUE).build();
    }

    @Bean
    Binding hotelCancelBinding() {
        return BindingBuilder.bind(hotelCancelQueue())
                .to(bookingExchange()).with(HOTEL_CANCEL_KEY);
    }

    @Bean
    Queue paymentCancelQueue() {
        return QueueBuilder.durable(PAYMENT_CANCEL_QUEUE).build();
    }

    @Bean
    Binding paymentCancelBinding() {
        return BindingBuilder.bind(hotelCancelQueue())
                .to(bookingExchange()).with(PAYMENT_CANCEL_KEY);
    }

    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange(BOOKING_EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_KEY);
    }

    @Bean
    Queue paymentCommandQueue() {
        return QueueBuilder.durable(PAYMENT_COMMAND_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_KEY)
                .build();
    }

    @Bean
    Binding paymentCommandBinding() {
        return BindingBuilder.bind(paymentCommandQueue())
                .to(bookingExchange()).with(PAYMENT_COMMAND_KEY);
    }

    @Bean
    public Queue flightCommandQueue() {
        return QueueBuilder.durable(FLIGHT_COMMAND_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_KEY)
                .build();
    }

    @Bean
    public Binding flightCommandBinding() {
        return BindingBuilder.bind(flightCommandQueue())
                .to(bookingExchange()).with(FLIGHT_COMMAND_KEY);
    }

    @Bean
    public Queue hotelCommandQueue() {
        return QueueBuilder.durable(HOTEL_COMMAND_QUEUE)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_KEY)
                .build();
    }

    @Bean
    public Binding hotelCommandBinding() {
        return BindingBuilder.bind(hotelCommandQueue())
                .to(bookingExchange()).with(HOTEL_COMMAND_KEY);
    }

    @Bean
    public Queue paymentReplyQueue() {
        return QueueBuilder.durable(PAYMENT_REPLY_QUEUE).build();
    }

    @Bean
    public Binding paymentReplyBinding() {
        return BindingBuilder.bind(paymentReplyQueue())
                .to(bookingExchange()).with(PAYMENT_REPLY_KEY);
    }

    @Bean
    public Queue flightReplyQueue() {
        return QueueBuilder.durable(FLIGHT_REPLY_QUEUE).build();
    }

    @Bean
    public Binding flightReplyBinding() {
        return BindingBuilder.bind(flightReplyQueue())
                .to(bookingExchange()).with(FLIGHT_REPLY_KEY);
    }

    @Bean
    public Queue hotelReplyQueue() {
        return QueueBuilder.durable(HOTEL_REPLY_QUEUE).build();
    }

    @Bean
    public Binding hotelReplyBinding() {
        return BindingBuilder.bind(hotelReplyQueue())
                .to(bookingExchange()).with(HOTEL_REPLY_KEY);
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connection) {
        RabbitTemplate template = new RabbitTemplate(connection);
        template.setMessageConverter(messageConverter());
        return template;
    }


}
