package org.example.service;

import org.example.model.roles.Customer;
import org.example.model.stock.CustomerMessage;
import org.example.repository.CustomerMessageRepository;
import org.example.repository.customer.CostumerRepository;
import org.example.repository.customer.CostumerRepositoryImp;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerMessageService {

    private final CustomerMessageRepository customerMessageRepository;
    private final CostumerRepository customerRepository;

    public CustomerMessageService(EntityManager entityManager) {
        this.customerMessageRepository = new CustomerMessageRepository(entityManager);
        this.customerRepository = new CostumerRepositoryImp(entityManager);
    }

    public void createMessage(List<Customer> customers, String storeName, String ingredientName, Integer quantity) {
        customerMessageRepository.createMessage(customers, storeName, ingredientName, quantity);
    }

    public List<CustomerMessage> getMessage(Customer customer) {
        return customerMessageRepository.getMessage(customer);
    }

    public List<CustomerMessage> getUnreadMessages(Customer customer) {
        return customerMessageRepository.getUnreadMessages(customer);
    }

    public void markAsRead(Customer customer) {
        customerMessageRepository.markAsRead(customer);
    }

    public void markMessageAsRead(long messageId) {
        customerMessageRepository.markMessageAsRead(messageId);
    }

    public void markAllAsRead(String username) {
        Customer customer = customerRepository.fetchCustomerByUsername(username);
        customerMessageRepository.markAllAsRead(customer);
    }
}
