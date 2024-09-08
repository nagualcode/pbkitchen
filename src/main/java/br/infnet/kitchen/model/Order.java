package br.infnet.kitchen.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public class Order {
    @Id
    private Long id;
    
    private String description;
    
    private double price;
    
    @Column("customer_id")
    private Long customerId;  // Mapeamento explícito para a coluna customer_id


    // Construtores
    public Order() {
    }

    public Order(Long id, String description, double price, Long customerId) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.customerId = customerId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", customerId=" + customerId +
                '}';
    }
}
