package org.example.service.nutritionist;

import org.example.model.Client;
import org.example.model.Nutritionist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.example.persistence.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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
