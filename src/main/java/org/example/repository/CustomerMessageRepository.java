package org.example.repository;

import org.example.model.roles.Customer;
import org.example.model.stock.CustomerMessage;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomerMessageRepository {

    EntityManager entityManager;

    public CustomerMessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createMessage(String message, List<Customer> customers) {
        entityManager.getTransaction().begin();
        for (Customer customer : customers) {
            CustomerMessage customerMessage = new CustomerMessage(customer.getCustomerId(), message);
            entityManager.persist(customerMessage);
        }
        entityManager.getTransaction().commit();
    }

    public List<CustomerMessage> getMessage(Customer customer) {
        return entityManager.createQuery("SELECT c FROM CUSTOMER_MESSAGE c WHERE c.customerId = :customerId", CustomerMessage.class)
                .setParameter("customerId", customer.getCustomerId())
                .getResultList();
    }
}
