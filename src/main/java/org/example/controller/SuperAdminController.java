package org.example.controller;
import org.example.service.SuperAdminService;

import org.example.model.roles.SuperAdmin;
import org.example.repository.superadmin.SuperAdminRepositoryImpl;

import javax.persistence.EntityManager;

public class SuperAdminController {

    SuperAdminService superAdminService;

    public SuperAdminController(EntityManager entityManager) {
        superAdminService = new SuperAdminService(entityManager);
    }

    public void createSuperAdmin(String username, String password) {
        superAdminService.createSuperAdmin(username, password);
    }

    public void readSuperAdmin(Long id) {
        superAdminService.readSuperAdmin(id);
    }

    public void updateSuperAdmin(Long id, String username, String password) {
        superAdminService.updateSuperAdmin(id, username, password);
    }

    public void deleteSuperAdmin(Long id) {
        superAdminService.deleteSuperAdmin(id);
    }

}
