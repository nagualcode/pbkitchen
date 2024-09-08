package br.infnet.kitchen.controller;

import br.infnet.kitchen.model.Order;
import br.infnet.kitchen.service.OrderService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint para criar um novo pedido
    @PostMapping
    public Mono<Order> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Endpoint para listar todos os pedidos
    @GetMapping
    public Flux<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
