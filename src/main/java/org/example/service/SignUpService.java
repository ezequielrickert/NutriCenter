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

public class SignUpService {
    private EntityManager entityManager;
    private CostumerRepository customerRepository;
    private NutritionistRepository nutritionistRepository;
    private StoreRepository storeRepository;
    private SuperAdminRepository superAdminRepository;

    public SignUpService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerRepository = new CostumerRepositoryImp(entityManager);
        this.nutritionistRepository = new NutritionistRepositoryImp(entityManager);
        this.storeRepository = new StoreRepositoryImpl(entityManager);
        this.superAdminRepository = new SuperAdminRepositoryImpl(entityManager);
    }

    public boolean validate(String username) {
        Customer customer = customerRepository.fetchCustomerByUsername(username);
        Nutritionist nutritionist = nutritionistRepository.fetchNutritionistByUsername(username);
        Store store = storeRepository.fetchStoreByName(username);
        SuperAdmin admin = superAdminRepository.fetchSuperAdminByName(username);
        return customer == null && nutritionist == null && store == null && admin == null;
    }

}
