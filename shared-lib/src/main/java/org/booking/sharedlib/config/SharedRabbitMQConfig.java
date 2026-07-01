package org.booking.sharedlib.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.booking.sharedlib.config.RabbitMQConstants.*;

@Configuration
public class SharedRabbitMQConfig {

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
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_KEY);
    }

    @Bean
    public Declarables commandQueues() {
        List<Declarable> all = new ArrayList<>();
        all.addAll(createCommandQueueAndBinding(FLIGHT_COMMAND_QUEUE, FLIGHT_COMMAND_KEY));
        all.addAll(createCommandQueueAndBinding(HOTEL_COMMAND_QUEUE, HOTEL_COMMAND_KEY));
        all.addAll(createCommandQueueAndBinding(PAYMENT_COMMAND_QUEUE, PAYMENT_COMMAND_KEY));
        all.addAll(createCommandQueueAndBinding(USER_COMMAND_QUEUE, USER_COMMAND_KEY));
        return new Declarables(all);
    }

    @Bean
    public Declarables replyQueues() {
        return createBeansQueues(Map.of(
                FLIGHT_REPLY_QUEUE, FLIGHT_REPLY_KEY,
                HOTEL_REPLY_QUEUE, HOTEL_REPLY_KEY,
                PAYMENT_REPLY_QUEUE, PAYMENT_REPLY_KEY,
                USER_REPLY_QUEUE, USER_REPLY_KEY
        ));
    }

    @Bean
    public Declarables cancelQueues() {
        return createBeansQueues(Map.of(
                FLIGHT_CANCEL_QUEUE, FLIGHT_CANCEL_KEY,
                HOTEL_CANCEL_QUEUE, HOTEL_CANCEL_KEY,
                PAYMENT_CANCEL_QUEUE, PAYMENT_CANCEL_KEY,
                USER_CANCEL_QUEUE, USER_CANCEL_KEY
        ));
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

    private Declarables createBeansQueues(Map<String, String> queues) {
        return new Declarables(
                queues.entrySet().stream()
                        .flatMap(queue -> createStandardQueueAndBinding(queue.getKey(),queue.getValue()).stream())
                        .toList()
        );
    }

    private List<Declarable> createStandardQueueAndBinding(String queueName, String routingKey) {
        Queue queue = QueueBuilder.durable(queueName).build();
        Binding binding = BindingBuilder.bind(queue).to(bookingExchange()).with(routingKey);
        return List.of(queue, binding);
    }

    private List<Declarable> createCommandQueueAndBinding(String queueName, String routingKey) {
        Queue queue = QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_KEY)
                .build();
        Binding binding = BindingBuilder.bind(queue).to(bookingExchange()).with(routingKey);
        return List.of(queue, binding);
    }

}
