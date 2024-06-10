package org.example.model.stock;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

@Entity(name = "CUSTOMER_MESSAGE")
public class CustomerMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Expose
    @Column(nullable = false)
    private String message;

    public CustomerMessage(Long customerId, String message) {
        this.customerId = customerId;
        this.message = message;
    }

    public CustomerMessage() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}