package org.example.service.nutritionist;

import org.example.model.Customer;
import org.example.model.Nutritionist;
import org.example.model.Store;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class NutritionistRepositoryImp implements NutritionistRepository{


  EntityManager entityManager;
  public NutritionistRepositoryImp(EntityManager entityManager){
    this.entityManager = entityManager;
  }
  @Override
  public void createNutritionist(String username, String email, String diploma) {
    entityManager.getTransaction().begin();

    Nutritionist nutritionist = new Nutritionist(username, email, diploma);

    entityManager.persist(nutritionist);

    entityManager.getTransaction().commit();
    entityManager.getTransaction();
  }

  @Override
  public Nutritionist readNutritionist(Long inNutritionistId) {
    entityManager.getTransaction().begin();
    Nutritionist nutritionist = entityManager.find(Nutritionist.class, inNutritionistId);
    entityManager.getTransaction().commit();
    return nutritionist;
  }

  @Override
  public void updateNutritionist(Long nutritionistId, String username, String email, String diploma) {
    entityManager.getTransaction().begin();
    Nutritionist nutritionist = entityManager.find(Nutritionist.class, nutritionistId);
    nutritionist.setNutritionistName(username);
    nutritionist.setNutritionistEmail(email);
    nutritionist.setEducationDiploma(diploma);
    entityManager.persist(nutritionist);
    entityManager.getTransaction().commit();
  }

  @Override
  public void deleteNutritionist(Long nutritionistId) {
    entityManager.getTransaction().begin();

    Nutritionist nutritionist = entityManager.find(Nutritionist.class, nutritionistId);
    if (nutritionist != null) {
      entityManager.remove(nutritionist);
    }

    entityManager.getTransaction().commit();
  }
}
