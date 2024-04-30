package org.example.model.roles;

import javax.persistence.*;

@Entity(name = "SUPER_ADMIN")
public class SuperAdmin {

    @Id
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, unique = true)
    private String adminUsername;

    @Column(nullable = false, unique = false)
    private String adminPassword;

    public SuperAdmin() {

    }

    public SuperAdmin(String adminUsername, String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public String getAdminUsername() { return adminUsername; }

    public Long getAdminId() {
        return adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
}
