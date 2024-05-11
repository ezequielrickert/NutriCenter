package org.example.controller;
import org.example.model.roles.Nutritionist;
import org.example.repository.nutritionist.NutritionistRepositoryImp;

import javax.persistence.EntityManager;
import org.example.service.NutritionistService;

public class NutritionistController {

  NutritionistService nutritionistService;

  public NutritionistController(EntityManager entityManager) {
    this.nutritionistService = new NutritionistService(entityManager);
  }

  public void createNutritionist(String username, String email, String password, String diploma){
    nutritionistService.createNutritionist(username, email, password, diploma);
  }

  public Nutritionist getNutritionist(Long nutritionistId) {
    return nutritionistService.getNutritionist(nutritionistId);
  }

  public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma) {
    nutritionistService.updateNutritionist(nutritionistId, username,email, password, diploma);
  }

  public void deleteNutritionist(Long nutritionistId) {
    nutritionistService.deleteNutritionist(nutritionistId);
  }
}
