package br.infnet.kitchen.controller;

import br.infnet.kitchen.model.Order;
import br.infnet.kitchen.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest(OrderController.class)
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new OrderController(orderService)).build();
    }

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        Order order = new Order();
        order.setDescription("Test order");
        order.setPrice(100.0);
        order.setCustomerId(1L);

        when(orderService.createOrder(any(Order.class))).thenReturn(Mono.just(order));

        webTestClient.post()
                .uri("/orders")
                .body(fromValue(order))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.description").isEqualTo("Test order");

        verify(orderService, times(1)).createOrder(any(Order.class));
    }
}
