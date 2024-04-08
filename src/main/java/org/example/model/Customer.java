package org.example.model;

import com.google.gson.Gson;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Entity(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, unique = false)
    private String customerName;

    @Column(nullable = false, unique = true)
    private String customerEmail;

    @Column(nullable = false, unique = false)
    private String customerPassword;


    public Customer(String customerName, String customerEmail, String customerPassword) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    public Customer() {

    }


    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public void setCustomerEmail(String mail) {
        this.customerEmail = mail;
    }

    public void setCustomerPassword(String password) { this.customerPassword = password; }

    public Long getCustomerId() {
    return customerId;
  }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPassword() { return customerPassword; }

  public String asJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
