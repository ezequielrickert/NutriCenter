package org.example.controller;

import org.example.service.nutritionist.NutritionistRepositoryImp;

public class NutritionistController {

  NutritionistRepositoryImp nutritionistRepositoryImp;
  public NutritionistController() {
    this.nutritionistRepositoryImp = new NutritionistRepositoryImp();
  }

  public void createNutritionist(String username, String email, String diploma){
    nutritionistRepositoryImp.createNutritionist(username, email, diploma);
  }
}
