package org.example.service;

import org.example.model.roles.Nutritionist;
import org.example.repository.nutritionist.NutritionistRepositoryImp;
import javax.persistence.EntityManager;
import java.util.List;

public class NutritionistService {

    NutritionistRepositoryImp nutritionistRepositoryImp;

    public NutritionistService(EntityManager entityManager) {
        this.nutritionistRepositoryImp = new NutritionistRepositoryImp(entityManager);
    }

    public void createNutritionist(String username, String email, String password, String diploma){
        nutritionistRepositoryImp.createNutritionist(username, email, password, diploma);
    }

    public Nutritionist getNutritionist(Long nutritionistId) {
        return nutritionistRepositoryImp.readNutritionist(nutritionistId);
    }

    public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma) {
        nutritionistRepositoryImp.updateNutritionist(nutritionistId, username,email, password, diploma);
    }

    public void deleteNutritionist(Long nutritionistId) {
        nutritionistRepositoryImp.deleteNutritionist(nutritionistId);
    }


    public Nutritionist getNutritionistByUsername(String username) {
        return nutritionistRepositoryImp.fetchNutritionistByUsername(username);
    }

    public List<Nutritionist> nutririonistWildcard(String username) {
        return nutritionistRepositoryImp.fetchNutritionistWildcard(username);
    }
}
