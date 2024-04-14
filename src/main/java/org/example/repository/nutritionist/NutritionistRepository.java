package org.example.repository.nutritionist;

import org.example.model.Nutritionist;

public interface NutritionistRepository {
  public void createNutritionist(String username, String email, String password, String diploma);

  public Nutritionist readNutritionist(Long inNutritionistId);

  public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma);

  public void deleteNutritionist(Long nutritionistId);
}
