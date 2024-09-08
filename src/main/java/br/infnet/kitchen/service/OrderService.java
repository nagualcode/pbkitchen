package br.infnet.kitchen.service;

import br.infnet.kitchen.model.Order;
import br.infnet.kitchen.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    // Método para criar um novo pedido e publicar no RabbitMQ
    public Mono<Order> createOrder(Order order) {
        return Mono.fromCallable(() -> {
            Order savedOrder = orderRepository.save(order);
            rabbitTemplate.convertAndSend("your-exchange", "orders.key", savedOrder.getId());  // Enviar apenas o ID para o RabbitMQ
            return savedOrder;
        });
    }

    // Método para buscar todos os pedidos
    public Flux<Order> getAllOrders() {
        return Flux.defer(() -> Flux.fromIterable(orderRepository.findAll()));
    }
}
