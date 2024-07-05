package org.example.model.stock;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity(name = "CUSTOMER_MESSAGE")
public class CustomerMessage {

    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    @Column(nullable = false)
    private Long customerId;

    @Expose
    @Column(nullable = false)
    private String message;

    @Expose
    @Column(nullable = false)
    private boolean isRead = false;

    public CustomerMessage(Long customerId, String message) {
        this.customerId = customerId;
        this.message = message;
        this.isRead = false;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
