package org.example.service;

import org.example.model.recipe.Allergy;
import org.example.repository.allergy.AllergyRepositoryImp;
import javax.persistence.EntityManager;
import java.util.List;

public class AllergyService {

    EntityManager entityManager;

    AllergyRepositoryImp allergyRepository;

    public AllergyService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.allergyRepository = new AllergyRepositoryImp(entityManager);
    }

    public void createAllergy(String name, String description) {
        allergyRepository.createAllergy(name, description);
    }

    public void readAllergy(long allergyId) {
        allergyRepository.readAllergy(allergyId);
    }

    public void updateAllergy(long allergyId, String description) {
        allergyRepository.updateAllergy(allergyId, description);
    }

    public void deleteAllergy(long allergyId) {
        allergyRepository.deleteAllergy(allergyId);
    }

    public List<Allergy> getAllergiesOrderedByName() {
        List<Allergy> result = allergyRepository.getAll();
        return result;
    }
}
