package org.example.repository.customer;

import org.example.model.Customer;

public interface CostumerRepository {

  public void createUser(String username, String email, String password);

  public Customer readUser(Long clientId);

  public void updateUser(Long clientId, String username, String email);

  public void deleteUser(Long clientId);

  Customer fetchCustomerByUsername(String username);

}