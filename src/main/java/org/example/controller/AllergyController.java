package org.example.controller;

import org.example.service.AllergyService;
import org.example.model.recipe.Allergy;
import javax.persistence.EntityManager;
import java.util.List;

public class AllergyController {

    AllergyService allergyService;

    public AllergyController(EntityManager entityManager) {
        allergyService = new AllergyService(entityManager);
    }

    public void createAllergy(String name, String description) {
        allergyService.createAllergy(name, description);
    }

    public void readAllergy(long allergyId) {
        allergyService.readAllergy(allergyId);
    }

    public void updateAllergy(long allergyId, String description) {
        allergyService.updateAllergy(allergyId, description);
    }

    public void deleteAllergy(long allergyId) {
        allergyService.deleteAllergy(allergyId);
    }

    public List<Allergy> getAllergiesOrderedByName() {
        List<Allergy> result = allergyService.getAllergiesOrderedByName();
        return result;
    }
}
