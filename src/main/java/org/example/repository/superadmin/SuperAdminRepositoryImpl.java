package org.example.repository.superadmin;

import org.example.model.Customer;
import org.example.model.SuperAdmin;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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

    @Override
    public SuperAdmin fetchSuperAdminByName(String username){
        entityManager.getTransaction().begin();
        try {
            SuperAdmin superAdmin = entityManager.createQuery("SELECT c FROM SUPER_ADMIN c WHERE c.adminUsername = :username", SuperAdmin.class)
                    .setParameter("username", username)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return superAdmin;
        } catch (NoResultException e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }
}
