package org.example.repository.customer;

import org.example.model.roles.Customer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;


public class CostumerRepositoryImp implements CostumerRepository {

  EntityManager entityManager;

  public CostumerRepositoryImp(EntityManager entityManager){
    this.entityManager = entityManager;
  }

  @Override
  public void createUser(String username, String email, String password) {
    entityManager.getTransaction().begin();
    Customer customer = new Customer();
    customer.setCustomerName(username);
    customer.setCustomerEmail(email);
    customer.setCustomerPassword(password);
    entityManager.persist(customer);
    entityManager.getTransaction().commit();
  }

  @Override
  public Customer readUser(Long clientId) {
    entityManager.getTransaction().begin();
    Customer customer = entityManager.find(Customer.class, clientId);
    entityManager.getTransaction().commit();
    return customer;
  }

  @Override
  public void updateUser(Long clientId, String username, String email) {
    entityManager.getTransaction().begin();
    Customer customer = entityManager.find(Customer.class, clientId);
    customer.setCustomerName(username);
    customer.setCustomerEmail(email);
    entityManager.persist(customer);
    entityManager.getTransaction().commit();
  }

  @Override
  public void deleteUser(Long clientId) {
    entityManager.getTransaction().begin();

    Customer customer = entityManager.find(Customer.class, clientId);
    if (customer != null) {
      entityManager.remove(customer);
    }
    entityManager.getTransaction().commit();
  }

  @Override
  public Customer fetchCustomerByUsername(String username) {
    entityManager.getTransaction().begin();
    try {
      Customer customer = entityManager.createQuery("SELECT c FROM CUSTOMER c WHERE c.customerName = :username", Customer.class)
              .setParameter("username", username)
              .getSingleResult();
      entityManager.getTransaction().commit();
      return customer;
    } catch (NoResultException e) {
      entityManager.getTransaction().rollback();
      return null;
    }
  }
}
