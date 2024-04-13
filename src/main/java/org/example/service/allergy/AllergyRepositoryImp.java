package org.example.service.allergy;
import org.example.model.Allergy;
import javax.persistence.EntityManager;

public class AllergyRepositoryImp implements AllergyRepository {

    EntityManager entityManager;

    public AllergyRepositoryImp(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void createAllergy(String allergyName, String description) {
        entityManager.getTransaction().begin();
        Allergy allergy = new Allergy(allergyName, description);
        entityManager.persist(allergy);
        entityManager.getTransaction().commit();
    }

    @Override
    public Allergy readAllergy(Long allergyId) {
        entityManager.getTransaction().begin();
        Allergy allergy = entityManager.find(Allergy.class, allergyId);
        entityManager.getTransaction().commit();
        return allergy;
    }

    @Override
    public void updateAllergy(Long allergyId, String description) {
        entityManager.getTransaction().begin();
        Allergy allergy = entityManager.find(Allergy.class, allergyId);
        allergy.setDescription(description);
        entityManager.persist(allergy);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteAllergy(Long allergyId) {
        entityManager.getTransaction().begin();
        Allergy allergy = entityManager.find(Allergy.class, allergyId);
        if (allergy != null) {
            entityManager.remove(allergy);
        }
        entityManager.getTransaction().commit();
    }
}
