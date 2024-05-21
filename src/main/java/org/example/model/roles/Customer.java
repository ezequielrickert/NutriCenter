package org.example.model.roles;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.example.model.history.CustomerHistory;

import javax.persistence.*;

@Entity(name = "CUSTOMER")
public class Customer {

    @Id
    @Expose
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Expose
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerHistoryId", referencedColumnName = "customerHistoryId")
    private CustomerHistory customerHistory;

    @Expose
    @Column(nullable = false, unique = false)
    private String customerName;

    @Expose
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

    public CustomerHistory getCustomerHistory() {
        return customerHistory;
    }

    public void setCustomerHistory(CustomerHistory customerHistory) {
        this.customerHistory = customerHistory;
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
