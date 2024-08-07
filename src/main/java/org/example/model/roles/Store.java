package org.example.model.roles;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.List;

@Entity(name = "STORE")
public class Store {

    @Id
    @Expose(serialize = true)
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Expose
    @Column(nullable = false, unique = true)
    private String storeName;

    @Expose
    @Column(nullable = false, unique = true)
    private String storeMail;

    @Expose
    @Column(nullable = false, unique = false)
    private String storePassword;

    @Expose
    @Column(nullable = true, unique = true)
    private String storePhoneNumber;

    @Expose
    @ManyToMany(mappedBy = "stores")
    private List<Customer> customers;

    public Store(String name, String mail, String password, String phoneNumber) {
        this.storeName = name;
        this.storeMail = mail;
        this.storePassword = password;
        this.storePhoneNumber = phoneNumber;
    }

    public Store() {}

    public Long getStoreId() { return storeId; }

    public String getStoreName() { return storeName; }

    public String getStoreMail() { return storeMail; }

    public String getStorePassword() { return storePassword; }

    public String getStorePhoneNumber() { return storePhoneNumber; }

    public void setStoreId(Long storeId) { this.storeId = storeId; }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreMail(String storeEmail) {
        this.storeMail = storeEmail;
    }

    public void setStorePassword(String storePassword) { this.storePassword = storePassword; }

    public void setStorePhoneNumber(String storePhoneNumber) {
        this.storePhoneNumber = storePhoneNumber;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}