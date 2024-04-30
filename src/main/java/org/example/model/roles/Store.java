package org.example.model.roles;

import com.google.gson.Gson;
import javax.persistence.*;

@Entity(name = "STORE")
public class Store {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false, unique = true)
    private String storeName;

    @Column(nullable = false, unique = true)
    private String storeMail;

    @Column(nullable = false, unique = false)
    private String storePassword;

    @Column(nullable = true, unique = true)
    private String storePhoneNumber;

    public Store(String name, String mail, String password) {
        this.storeName = name;
        this.storeMail = mail;
        this.storePassword = password;
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

    public String asJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
