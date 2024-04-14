package org.example.repository.allergy;
import org.example.model.Allergy;

public interface AllergyRepository {
    void createAllergy(String allergyName, String description);
    Allergy readAllergy(Long allergyId);
    void updateAllergy(Long allergyId, String description);
    void deleteAllergy(Long allergyId);
}
