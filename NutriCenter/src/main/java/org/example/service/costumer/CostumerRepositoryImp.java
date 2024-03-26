package org.example.service.costumer;

import org.example.model.Customer;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class CostumerRepositoryImp implements CostumerRepository {

  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

  @Override
  public void createUser(String username, String email) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Customer customer = new Customer();
    customer.setClientName(username);
    customer.setClientEmail(email);
    entityManager.persist(customer);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  @Override
  public Customer readUser(Long clientId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Customer customer = entityManager.find(Customer.class, clientId);
    entityManager.getTransaction().commit();
    entityManager.close();
    return customer;
  }

  @Override
  public void updateUser(Long clientId, String username, String email) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();
    Customer customer = entityManager.find(Customer.class, clientId);
    customer.setClientName(username);
    customer.setClientEmail(email);
    entityManager.persist(customer);
    entityManager.getTransaction().commit();
    entityManager.close();
  }

  @Override
  public void deleteUser(Long clientId) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    Customer customer = entityManager.find(Customer.class, clientId);
    if (customer != null) {
      entityManager.remove(customer);
    }

    entityManager.getTransaction().commit();
    entityManager.close();
  }
}
