package org.example.service;

import org.example.model.roles.Customer;
import org.example.model.stock.CustomerMessage;
import org.example.repository.CustomerMessageRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerMessageService {

    CustomerMessageRepository customerMessageRepository;

    public CustomerMessageService(EntityManager entityManager) {
        customerMessageRepository = new CustomerMessageRepository(entityManager);
    }

    public void createMessage(String message, List<Customer> customers) {
        customerMessageRepository.createMessage(message, customers);
    }

    public List<CustomerMessage> getMessage(Customer customer) {
        return customerMessageRepository.getMessage(customer);
    }
}
