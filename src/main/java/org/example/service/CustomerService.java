package org.example.service;

import org.checkerframework.checker.units.qual.C;
import org.example.model.Customer;
import org.example.repository.costumer.CostumerRepository;
import org.example.repository.costumer.CostumerRepositoryImp;

import javax.persistence.EntityManager;


public class CustomerService {

    private EntityManager entityManager;
    private CostumerRepository customerRepository;

    public CustomerService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerRepository = new CostumerRepositoryImp(entityManager);
    }

    public String createUser(String username, String email, String password) {
        try {
            customerRepository.createUser(username, email, password);
            return "Customer created succesfully";
        }
        catch (Error error) {
            return "Invalid username or email";
        }
    }

    public Customer readUser(Long inClientId) {
        return customerRepository.readUser(inClientId);
    }

    public void updateUser(Long clientId, String username, String email) {
        customerRepository.updateUser(clientId, username, email);
    }

    public void deleteUser(Long clientId) {
        customerRepository.deleteUser(clientId);
    }
}
