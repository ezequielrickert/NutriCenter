package org.example.repository.allergy;
import org.example.model.recipe.Allergy;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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

    public List<Allergy> getAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Allergy> cq = cb.createQuery(Allergy.class);
        Root<Allergy> root = cq.from(Allergy.class);
        cq.select(root);
        cq.orderBy(cb.asc(root.get("allergyName")));
        return entityManager.createQuery(cq).getResultList();
    }
}
