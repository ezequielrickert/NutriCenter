package org.example.repository.customer;

import org.example.model.history.CustomerHistory;
import org.example.model.history.WeightHistory;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;
import org.example.model.roles.Store;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


public class CostumerRepositoryImp implements CostumerRepository {

  EntityManager entityManager;

  public CostumerRepositoryImp(EntityManager entityManager){
    this.entityManager = entityManager;
  }

  @Override
  public void createUser(String username, String email, String password, CustomerHistory customerHistory) {
    if (!entityManager.getTransaction().isActive()) {
      entityManager.getTransaction().begin();
    }
    Customer customer = new Customer();
    customer.setCustomerName(username);
    customer.setCustomerEmail(email);
    customer.setCustomerPassword(password);
    customer.setCustomerHistory(customerHistory);
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
  public void updateUser(Long clientId, String username, String email, List<Nutritionist> nutritionists, List<Store> stores, List<Ingredient> ingredients, List<WeightHistory> weightHistory) {
    entityManager.getTransaction().begin();
    Customer customer = entityManager.find(Customer.class, clientId);
    customer.setCustomerName(username);
    customer.setCustomerEmail(email);
    customer.setNutritionists(nutritionists);
    customer.setStores(stores);
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

  @Override
  public void unsubscribe(String nutritionist, Customer customer) {
    entityManager.getTransaction().begin();
    List<Nutritionist> subscriptions = customer.getNutritionists();
    for (Nutritionist n : subscriptions) {
      if (n.getNutritionistName().equals(nutritionist)) {
        subscriptions.remove(n);
        break;
      }
    }
    customer.setNutritionists(subscriptions);
    entityManager.persist(customer);
    entityManager.getTransaction().commit();
  }
}
