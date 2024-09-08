package br.infnet.kitchen.listener;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

@Configuration
public class RabbitMQConfig {

    @Bean
    Queue ordersQueue() {
        return new Queue("orders", true);  
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("your-exchange");
    }

    @Bean
    Binding binding(Queue ordersQueue, TopicExchange exchange) {
        return BindingBuilder.bind(ordersQueue).to(exchange).with("orders.key");  
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
