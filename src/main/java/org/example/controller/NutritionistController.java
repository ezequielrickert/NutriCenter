package org.example.controller;

import org.example.model.Nutritionist;
import org.example.repository.nutritionist.NutritionistRepositoryImp;

import javax.persistence.EntityManager;

public class NutritionistController {

  NutritionistRepositoryImp nutritionistRepositoryImp;
  public NutritionistController(EntityManager entityManager) {
    this.nutritionistRepositoryImp = new NutritionistRepositoryImp(entityManager);
  }

  public void createNutritionist(String username, String email, String password, String diploma){
    nutritionistRepositoryImp.createNutritionist(username, email, password, diploma);
  }

  public Nutritionist getNutritionist(Long nutritionistId) {
    return nutritionistRepositoryImp.readNutritionist(nutritionistId);
  }

  public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma) {
    nutritionistRepositoryImp.updateNutritionist(nutritionistId, username,email, password, diploma);
  }

  public void deleteNutritionist(Long nutritionistId) {
    nutritionistRepositoryImp.deleteNutritionist(nutritionistId);
  }
}
