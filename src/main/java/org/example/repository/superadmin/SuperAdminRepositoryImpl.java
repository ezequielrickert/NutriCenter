package org.example.repository.superadmin;

import org.example.model.SuperAdmin;

import javax.persistence.EntityManager;

public class SuperAdminRepositoryImpl implements SuperAdminRepository{

    EntityManager entityManager;

    public SuperAdminRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createSuperAdmin(String username, String password) {
        entityManager.getTransaction().begin();
        SuperAdmin superAdmin = new SuperAdmin(username, password);
        entityManager.persist(superAdmin);
        entityManager.getTransaction().commit();
    }

    @Override
    public SuperAdmin readSuperAdmin(Long id) {
        entityManager.getTransaction().begin();
        SuperAdmin superAdmin = entityManager.find(SuperAdmin.class, id);
        entityManager.getTransaction().commit();
        return superAdmin;
    }

    @Override
    public void updateSuperAdmin(Long id, String username, String password) {
        entityManager.getTransaction().begin();
        SuperAdmin superAdmin = entityManager.find(SuperAdmin.class, id);
        superAdmin.setAdminId(id);
        superAdmin.setAdminUsername(username);
        superAdmin.setAdminPassword(password);
        entityManager.persist(superAdmin);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteSuperAdmin(Long id) {
        entityManager.getTransaction().begin();
        SuperAdmin superAdmin = entityManager.find(SuperAdmin.class, id);
        if (superAdmin != null) {
            entityManager.remove(superAdmin);
        }

        entityManager.getTransaction().commit();
    }
}
