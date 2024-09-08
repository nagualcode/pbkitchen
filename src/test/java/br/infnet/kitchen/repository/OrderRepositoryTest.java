package br.infnet.kitchen.repository;

import br.infnet.kitchen.model.Order;
import br.infnet.kitchen.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:tc:postgresql:16.0:///test_db",
    "spring.datasource.username=test",
    "spring.datasource.password=test"
})
public class OrderRepositoryTest {

    @Container
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @Container
    private static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3")
            .withExposedPorts(5672, 15672);

    @Autowired
    private OrderRepository orderRepository;

    @BeforeAll
    static void setUp() {
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());

        System.setProperty("spring.rabbitmq.host", rabbitMQContainer.getHost());
        System.setProperty("spring.rabbitmq.port", rabbitMQContainer.getMappedPort(5672).toString());
    }

    @Test
    void testSaveAndFindAllOrders() {
        Order order = new Order(null, "Test order", 50.0, 1L);
        orderRepository.save(order);

        List<Order> orders = (List<Order>) orderRepository.findAll();
        assertThat(orders).isNotEmpty();
    }
}
