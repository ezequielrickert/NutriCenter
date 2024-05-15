package org.example.repository.nutritionist;
import org.example.model.roles.Nutritionist;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class NutritionistRepositoryImp implements NutritionistRepository{


  EntityManager entityManager;
  public NutritionistRepositoryImp(EntityManager entityManager){
    this.entityManager = entityManager;
  }
  @Override
  public void createNutritionist(String username, String email, String password, String diploma) {
    entityManager.getTransaction().begin();

    Nutritionist nutritionist = new Nutritionist(username, email, password, diploma);

    entityManager.persist(nutritionist);

    entityManager.getTransaction().commit();
  }

  @Override
  public Nutritionist readNutritionist(Long inNutritionistId) {
    entityManager.getTransaction().begin();
    Nutritionist nutritionist = entityManager.find(Nutritionist.class, inNutritionistId);
    entityManager.getTransaction().commit();
    return nutritionist;
  }

  @Override
  public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma) {
    entityManager.getTransaction().begin();
    Nutritionist nutritionist = entityManager.find(Nutritionist.class, nutritionistId);
    nutritionist.setNutritionistName(username);
    nutritionist.setNutritionistEmail(email);
    nutritionist.setNutritionistPassword(password);
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

  @Override
  public Nutritionist fetchNutritionistByUsername(String username) {
    entityManager.getTransaction().begin();
    try {
      Nutritionist nutritionist = entityManager.createQuery("SELECT c FROM NUTRITIONIST c WHERE c.nutritionistName = :username", Nutritionist.class)
              .setParameter("username", username)
              .getSingleResult();
      entityManager.getTransaction().commit();
      return nutritionist;
    } catch (NoResultException e) {
      entityManager.getTransaction().rollback();
      return null;
    }
  }

  public List<Nutritionist> fetchNutritionistWildcard(String username) {
    entityManager.getTransaction().begin();
    List<Nutritionist> nutritionist = entityManager.createQuery("SELECT c FROM NUTRITIONIST c WHERE c.nutritionistName LIKE :username", Nutritionist.class)
            .setParameter("username", "%" + username + "%")
            .getResultList();
    entityManager.getTransaction().commit();
    return nutritionist;
  }
}
