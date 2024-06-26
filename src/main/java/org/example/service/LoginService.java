package org.example.service;

import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;
import org.example.model.roles.Store;
import org.example.model.roles.SuperAdmin;
import org.example.repository.customer.CostumerRepository;
import org.example.repository.customer.CostumerRepositoryImp;
import org.example.repository.nutritionist.NutritionistRepository;
import org.example.repository.nutritionist.NutritionistRepositoryImp;
import org.example.repository.store.StoreRepository;
import org.example.repository.store.StoreRepositoryImpl;
import org.example.repository.superadmin.SuperAdminRepository;
import org.example.repository.superadmin.SuperAdminRepositoryImpl;

import javax.persistence.EntityManager;

public class LoginService {

    private EntityManager entityManager;
    private CostumerRepository customerRepository;
    private NutritionistRepository nutritionistRepository;
    private StoreRepository storeRepository;
    private SuperAdminRepository superAdminRepository;

    public LoginService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerRepository = new CostumerRepositoryImp(entityManager);
        this.nutritionistRepository = new NutritionistRepositoryImp(entityManager);
        this.storeRepository = new StoreRepositoryImpl(entityManager);
        this.superAdminRepository = new SuperAdminRepositoryImpl(entityManager);
    }

    public String fetchUser(String username) {
        try {
            Customer customer = customerRepository.fetchCustomerByUsername(username);
            if (customer != null) {
                return customer.getCustomerPassword();
            }
            Nutritionist nutritionist = nutritionistRepository.fetchNutritionistByUsername(username);
            if (nutritionist != null) {
                return nutritionist.getNutritionistPassword();
            }

            Store store = storeRepository.fetchStoreByName(username);
            if (store != null) {
                return store.getStorePassword();
            }

            SuperAdmin admin = superAdminRepository.fetchSuperAdminByName(username);
            if (admin != null) {
                return admin.getAdminPassword();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public String fetchUserRol(String username) {
        try {
            Customer customer = customerRepository.fetchCustomerByUsername(username);
            if (customer != null) {
                return "customer";
            }
            Nutritionist nutritionist = nutritionistRepository.fetchNutritionistByUsername(username);
            if (nutritionist != null) {
                return "nutritionist";
            }

            Store store = storeRepository.fetchStoreByName(username);
            if (store != null) {
                return "store";
            }

            SuperAdmin admin = superAdminRepository.fetchSuperAdminByName(username);
            if (admin != null) {
                return "superAdmin";
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
