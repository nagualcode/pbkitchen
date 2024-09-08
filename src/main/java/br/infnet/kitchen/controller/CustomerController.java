package br.infnet.kitchen.controller;

import br.infnet.kitchen.model.Customer;
import br.infnet.kitchen.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Endpoint para criar um novo costumer
    @PostMapping
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return Mono.fromCallable(() -> customerRepository.save(customer));
    }

    // Endpoint para listar todos os costumers
    @GetMapping
    public Flux<Customer> getAllCustumers() {
        return Flux.defer(() -> Flux.fromIterable(customerRepository.findAll()));
    }
}
