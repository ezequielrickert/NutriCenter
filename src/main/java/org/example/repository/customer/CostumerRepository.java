package org.example.repository.customer;

import org.example.model.history.CustomerHistory;
import org.example.model.history.WeightHistory;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;
import org.example.model.roles.Store;

import java.util.List;

public interface CostumerRepository {

  public void createUser(String username, String email, String password, CustomerHistory customerHistory);

  public Customer readUser(Long clientId);

  public void updateUser(Long clientId, String username, String email, List<Nutritionist> nutritionists,
                         List<Store> stores, List<Ingredient> ingredients, List<WeightHistory> weightHistory);

  public void deleteUser(Long clientId);

  Customer fetchCustomerByUsername(String username);

  void unsubscribe(String nutritionist, Customer customer);

}
