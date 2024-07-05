package org.example.service;

import org.example.model.roles.Customer;
import org.example.model.stock.CustomerMessage;
import org.example.repository.CustomerMessageRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerMessageService {

    private final CustomerMessageRepository customerMessageRepository;

    public CustomerMessageService(EntityManager entityManager) {
        this.customerMessageRepository = new CustomerMessageRepository(entityManager);
    }

    public void createMessage(String message, List<Customer> customers) {
        customerMessageRepository.createMessage(message, customers);
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
}
