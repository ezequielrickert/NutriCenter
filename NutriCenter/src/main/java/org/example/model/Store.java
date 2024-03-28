package org.example.model;

import javax.persistence.*;

@Entity(name = "STORE")
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

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreMail(String storeEmail) {
        this.storeMail = storeEmail;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public Store() {

    }
}
