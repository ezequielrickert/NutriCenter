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
    private boolean isRead = false;

    @Expose
    @Column(nullable = false)
    private String storeName;

    @Expose
    @Column(nullable = false)
    private String ingredientName;

    @Expose
    @Column
    private Integer quantity;

    public CustomerMessage(Long customerId, String storeName, String ingredientName, Integer quantity) {
        this.customerId = customerId;
        this.isRead = false;
        this.storeName = storeName;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public CustomerMessage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
