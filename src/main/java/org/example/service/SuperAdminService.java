package org.example.service;

import org.example.model.SuperAdmin;
import org.example.repository.superadmin.SuperAdminRepositoryImpl;

import javax.persistence.EntityManager;

public class SuperAdminService {

    SuperAdminRepositoryImpl superAdminRepository;

    public SuperAdminService(EntityManager entityManager) {
        superAdminRepository = new SuperAdminRepositoryImpl(entityManager);
    }

    public void createSuperAdmin(String username, String password) {
        superAdminRepository.createSuperAdmin(username, password);
    }

    public void readSuperAdmin(Long id) {
        SuperAdmin superAdmin = superAdminRepository.readSuperAdmin(id);
        if (superAdmin != null) {
            System.out.println("Client ID: " + superAdmin.getAdminId());
            System.out.println("Client Name: " + superAdmin.getAdminUsername());
        } else {
            System.out.println("Client not found");
        }
    }

    public void updateSuperAdmin(Long id, String username, String password) {
        superAdminRepository.updateSuperAdmin(id, username, password);
    }

    public void deleteSuperAdmin(Long id) {
        superAdminRepository.deleteSuperAdmin(id);
    }

}
