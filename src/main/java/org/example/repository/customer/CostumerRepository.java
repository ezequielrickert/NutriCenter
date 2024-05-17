package org.example.repository.customer;

import org.example.model.history.CustomerHistory;
import org.example.model.roles.Customer;

public interface CostumerRepository {

  public void createUser(String username, String email, String password, CustomerHistory customerHistory);

  public Customer readUser(Long clientId);

  public void updateUser(Long clientId, String username, String email);

  public void deleteUser(Long clientId);

  Customer fetchCustomerByUsername(String username);

}
