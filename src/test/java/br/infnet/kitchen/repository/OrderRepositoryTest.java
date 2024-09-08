package br.infnet.kitchen.repository;

import br.infnet.kitchen.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
public class OrderRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testSaveAndFindAllOrders() {
        Order order = new Order(null, "Test order", 50.0, 1L);
        orderRepository.save(order);

        List<Order> orders = (List<Order>) orderRepository.findAll();
        assertThat(orders).isNotEmpty();
    }
}
