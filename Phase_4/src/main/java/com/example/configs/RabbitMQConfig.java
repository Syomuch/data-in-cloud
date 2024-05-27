package com.example.configs;

import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.models.MovieActorAction;
import com.example.services.MovieActorActionService;

@Configuration
public class RabbitMQConfig {
    static final String queueName = "MyQueue";
    static final String exchangeName = "MyExchange";

    private final MovieActorActionService service;

    public RabbitMQConfig(MovieActorActionService service) {
        this.service = service;
    }

    @RabbitListener(queues = queueName)
    public void listen(MovieActorAction movieActorAction) {
        service.save(movieActorAction);
        System.out.println("Listen from queue: " + movieActorAction.toString());
    }

    @Bean
    public Queue StudAndDocQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("first.key").noargs();
    }

    @Bean
    public SimpleMessageConverter converter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("com.example.*", "java.util.*"));
        return converter;

    }
}
