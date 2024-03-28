package org.example.controller;

import org.example.model.Customer;
import org.example.service.costumer.CostumerRepositoryImp;

import javax.persistence.EntityManager;

public class CustomerController {
  CostumerRepositoryImp costumerRepositoryImp;
  public CustomerController(EntityManager entityManager) {
    this.costumerRepositoryImp = new CostumerRepositoryImp(entityManager);
  }
  public void createClient(String username, String email) {
    costumerRepositoryImp.createUser(username, email);
  }

  public void readClient(Long inClientId) {
    Customer customer = costumerRepositoryImp.readUser(inClientId);
    if (customer != null) {
      System.out.println("Client ID: " + customer.getClientId());
      System.out.println("Client Name: " + customer.getClientName());
      System.out.println("Client Email: " + customer.getClientEmail());
    } else {
      System.out.println("Client not found");
    }
  }

  public void updateClient(Long clientId, String username, String email) {
    costumerRepositoryImp.updateUser(clientId, username, email);
  }

  public void deleteClient(Long clientId) {
    costumerRepositoryImp.deleteUser(clientId);
  }
}
