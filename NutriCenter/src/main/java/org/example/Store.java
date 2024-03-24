package org.example;

import javax.persistence.*;

@Entity
public class Store {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false, unique = false)
    private String storeName;

    @Column(nullable = false, unique = true)
    private String storeMail;

    @Column(nullable = false, unique = true)
    private String storeNumber;

    public Store(String name, String mail, String number) {
        this.storeName = name;
        this.storeMail = mail;
        this.storeNumber = number;
    }

    public Store() {

    }
}
