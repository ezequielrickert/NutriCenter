package org.example.controller;

import org.example.model.roles.Customer;
import org.example.service.CustomerService;

import javax.persistence.EntityManager;

public class CustomerController {
  CustomerService customerService;
  public CustomerController(EntityManager entityManager) {
    this.customerService = new CustomerService(entityManager);
  }

  public void createClient(String username, String email, String password) {
    customerService.createUser(username, email, password);
  }

  public void readClient(Long inClientId) {
    Customer customer = this.customerService.readUser(inClientId);
    if (customer != null) {
      System.out.println("Client ID: " + customer.getCustomerId());
      System.out.println("Client Name: " + customer.getCustomerName());
      System.out.println("Client Email: " + customer.getCustomerEmail());
    } else {
      System.out.println("Client not found");
    }
  }

  public void updateClient(Long clientId, String username, String email) {
    customerService.updateUser(clientId, username, email);
  }

  public void deleteClient(Long clientId) {
    customerService.deleteUser(clientId);
  }

  public Customer getCustomerByName(String username){
    return customerService.getCustomerByName(username);
  }
}
