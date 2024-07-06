package org.example.repository;

import org.example.model.roles.Customer;
import org.example.model.stock.CustomerMessage;
import javax.persistence.EntityManager;
import java.util.List;

public class CustomerMessageRepository {

    private final EntityManager entityManager;

    public CustomerMessageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void createMessage(String message, List<Customer> customers) {
        entityManager.getTransaction().begin();
        for (Customer customer : customers) {
            CustomerMessage customerMessage = new CustomerMessage();
            customerMessage.setMessage(message);
            customerMessage.setCustomerId(customer.getCustomerId());
            customerMessage.setRead(false);
            entityManager.persist(customerMessage);
        }
        entityManager.getTransaction().commit();
    }

    public List<CustomerMessage> getMessage(Customer customer) {
        return entityManager.createQuery(
                        "SELECT m FROM CUSTOMER_MESSAGE m WHERE m.customerId = :customerId ORDER BY m.id DESC",
                        CustomerMessage.class)
                .setParameter("customerId", customer.getCustomerId())
                .getResultList();
    }

    public List<CustomerMessage> getUnreadMessages(Customer customer) {
        return entityManager.createQuery(
                        "SELECT m FROM CUSTOMER_MESSAGE m WHERE m.customerId = :customerId AND m.isRead = false ORDER BY m.id DESC",
                        CustomerMessage.class)
                .setParameter("customerId", customer.getCustomerId())
                .getResultList();
    }

    public void markAsRead(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.createQuery(
                        "UPDATE CUSTOMER_MESSAGE m SET m.isRead = true WHERE m.customerId = :customerId")
                .setParameter("customerId", customer.getCustomerId())
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void markMessageAsRead(long messageId) {
        entityManager.getTransaction().begin();
        entityManager.createQuery(
                        "UPDATE CUSTOMER_MESSAGE m SET m.isRead = true WHERE m.id = :id")
                .setParameter("id", messageId)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    public void markAllAsRead(Customer customer) {
        entityManager.getTransaction().begin();
        entityManager.createQuery(
                        "UPDATE CUSTOMER_MESSAGE m SET m.isRead = true WHERE m.customerId = :customerId")
                .setParameter("customerId", customer.getCustomerId())
                .executeUpdate();
        entityManager.getTransaction().commit();
    }
}
