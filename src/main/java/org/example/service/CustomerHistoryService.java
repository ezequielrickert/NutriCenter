package org.example.service;

import org.example.model.history.CustomerHistory;
import org.example.repository.customerhistory.CustomerHistoryRepository;
import org.example.repository.customerhistory.CustomerHistoryRepositoryImplementation;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.DayOfWeek;

public class CustomerHistoryService {

    private EntityManager entityManager;
    private CustomerHistoryRepository customerHistoryRepository;


    public CustomerHistoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.customerHistoryRepository = new CustomerHistoryRepositoryImplementation(entityManager);
    }

    public void createCustomerHistory() {
        customerHistoryRepository.createCustomerHistory();
    }

    public CustomerHistory readCustomerHistory(Long customerHistoryId){
        return customerHistoryRepository.readCustomerHistory(customerHistoryId);
    }

    public void deleteCustomerHistory(Long customerHistoryId){
        customerHistoryRepository.deleteCustomerHistory(customerHistoryId);
    }

    public CustomerHistory updateCustomerHistory(Long customerHistoryId, DayOfWeek dayName){
        return customerHistoryRepository.updateCustomerHistory(customerHistoryId, dayName);
    }
}
