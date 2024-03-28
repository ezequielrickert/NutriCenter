package org.example.model;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, unique = false)
    private String clientName;

    @Column(nullable = false, unique = true)
    private String clientEmail;


    public Customer(String clientName, String clientEmail) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
    }

    public Customer() {

    }


    public void setClientName(String name) {
        this.clientName = name;
    }

    public void setClientEmail(String mail) {
        this.clientEmail = mail;
    }

    public Long getClientId() {
    return clientId;
  }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

  public String asJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
