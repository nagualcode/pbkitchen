package br.infnet.kitchen.listener;

import br.infnet.kitchen.model.Order;
import br.infnet.kitchen.model.Customer;
import br.infnet.kitchen.repository.OrderRepository;
import br.infnet.kitchen.repository.CustomerRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderMessageListener {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderMessageListener(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @RabbitListener(queues = "orders")
    public void onMessage(Long orderId) {
        // Recuperar a Order pelo ID
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        Customer customer = customerRepository.findById(order.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Customer not found for Order: " + orderId));

        // Simular envio de e-mail (exibição no console)
        System.out.println("To: " + customer.getEmail());
        System.out.println("Subject: Details of your order #" + order.getId());
        System.out.println("Dear " + customer.getNome() + ",\nHere are the details of your order:\n" + order.getDescription() + " - $" + order.getPrice());
    }
}
