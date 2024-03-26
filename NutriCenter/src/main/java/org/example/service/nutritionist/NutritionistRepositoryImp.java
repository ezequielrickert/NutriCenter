package org.example.service.nutritionist;

import org.example.model.Nutritionist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class NutritionistRepositoryImp implements NutritionistRepository{

  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

  @Override
  public void createNutritionist(String username, String email, String diploma) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    entityManager.getTransaction().begin();

    Nutritionist nutritionist = new Nutritionist(username, email, diploma);

    entityManager.persist(nutritionist);

    entityManager.getTransaction().commit();
    entityManager.getTransaction();
  }

  @Override
  public Nutritionist readNutritionist(Long inNutritionistId) {
    return null;
  }

  @Override
  public void updateNutritionist(Long nutritionistId, String username, String email, String diploma) {

  }

  @Override
  public void deleteNutritionist(Long nutritionistId) {

  }
}
