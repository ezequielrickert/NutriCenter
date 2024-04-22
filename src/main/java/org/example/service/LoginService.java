package org.example.service;

import org.example.model.Customer;
import org.example.model.Nutritionist;
import org.example.model.Store;
import org.example.model.SuperAdmin;
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
}
