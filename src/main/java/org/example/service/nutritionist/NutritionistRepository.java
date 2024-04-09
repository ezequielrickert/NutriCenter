package org.example.service.nutritionist;

import org.example.model.Nutritionist;

import java.sql.ResultSet;

public interface NutritionistRepository {
  public void createNutritionist(String username, String email, String password, String diploma);

  public Nutritionist readNutritionist(Long inNutritionistId);

  public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma);

  public void deleteNutritionist(Long nutritionistId);
}
