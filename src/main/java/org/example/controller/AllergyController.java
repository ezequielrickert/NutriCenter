package org.example.controller;
import org.example.model.Allergy;
import org.example.repository.allergy.AllergyRepositoryImp;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AllergyController {

    AllergyRepositoryImp allergyRepositoryImp;

    public AllergyController(EntityManager entityManager) {
        allergyRepositoryImp = new AllergyRepositoryImp(entityManager);
    }

    public void createAllergy(String name, String description) {
        allergyRepositoryImp.createAllergy(name, description);
    }

    public void readAllergy(long allergyId) {
        Allergy allergy = allergyRepositoryImp.readAllergy(allergyId);
        if (allergy != null) {
            System.out.println("Allergy ID: " + allergy.getAllergyId());
            System.out.println("Allergy Name: " + allergy.getAllergyName());
            System.out.println("Allergy Description: " + allergy.getDescription());
        } else {
            System.out.println("Allergy not found");
        }
    }

    public void updateAllergy(long allergyId, String description) {
        allergyRepositoryImp.updateAllergy(allergyId, description);
    }

    public void deleteAllergy(long allergyId) {
        allergyRepositoryImp.deleteAllergy(allergyId);
    }

    public List<Allergy> getAllergiesOrderedByName(EntityManager entityManager) {
        List<Allergy> result = allergyRepositoryImp.getAll();
        return result;
    }
}
