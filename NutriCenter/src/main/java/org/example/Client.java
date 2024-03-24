package org.example;

import javax.persistence.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, unique = false)
    private String clientName;

    @Column(nullable = false, unique = true)
    private String clientEmail;


    public Client() {
        this.clientName = "Test";
        this.clientEmail = "test@test.Test";
    }


    public void setClientName(String name) {
        this.clientName = name;
    }

    public void setClientEmail(String mail) {
        this.clientEmail = mail;
    }
}
