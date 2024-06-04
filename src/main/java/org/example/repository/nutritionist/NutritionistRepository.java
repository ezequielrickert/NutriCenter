package org.example.repository.nutritionist;

import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;

import java.util.List;

public interface NutritionistRepository {
  public void createNutritionist(String username, String email, String password, String diploma);

  public Nutritionist readNutritionist(Long inNutritionistId);

  public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma, List<Customer> customers);

  public void deleteNutritionist(Long nutritionistId);

  Nutritionist fetchNutritionistByUsername(String username);
}
