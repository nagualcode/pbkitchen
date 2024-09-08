package br.infnet.kitchen.service;

import br.infnet.kitchen.model.Order;
import br.infnet.kitchen.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_shouldSaveOrderAndSendToQueue() {
        Order order = new Order();
        order.setDescription("Test order");
        order.setPrice(100.0);
        order.setCustomerId(1L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Mono<Order> result = orderService.createOrder(order);

        assertNotNull(result);
        result.subscribe(savedOrder -> {
            assertEquals("Test order", savedOrder.getDescription());
            verify(rabbitTemplate).convertAndSend("your-exchange", "orders.key", savedOrder.getId());
        });
    }

    @Test
    void getAllOrders_shouldReturnOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(new Order(), new Order()));

        var orders = orderService.getAllOrders().collectList().block();

        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }
}
