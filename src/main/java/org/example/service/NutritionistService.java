package org.example.service;

import org.example.model.roles.Customer;
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

    public void updateNutritionist(Long nutritionistId, String username, String email, String password, String diploma, List<Customer> customers) {
        nutritionistRepositoryImp.updateNutritionist(nutritionistId, username,email, password, diploma, customers);
    }

    public void deleteNutritionist(Long nutritionistId) {
        nutritionistRepositoryImp.deleteNutritionist(nutritionistId);
    }


    public Nutritionist getNutritionistByUsername(String username) {
        return nutritionistRepositoryImp.fetchNutritionistByUsername(username);
    }

    public List<Nutritionist> nutritionistWildcard(String username) {
        return nutritionistRepositoryImp.fetchNutritionistWildcard(username);
    }

    public void subscribe(Nutritionist nutritionist, Customer customer) {
        nutritionist.getCustomers().add(customer);
        nutritionistRepositoryImp.updateNutritionist(nutritionist.getNutritionistId(),
                nutritionist.getNutritionistName(), nutritionist.getNutritionistEmail(),
                nutritionist.getNutritionistPassword(), nutritionist.getEducationDiploma(),
                nutritionist.getCustomers()
        );
    }

    public void unsubscribe(String nutritionistName, Customer customer) {
        Nutritionist nutritionist = getNutritionistByUsername(nutritionistName);
        List<Customer> customerList = nutritionist.getCustomers();
        for (Customer customer1 : customerList) {
            if (customer1.getCustomerName().equals(customer.getCustomerName())) {
                customerList.remove(customer1);
                nutritionist.setCustomers(customerList);
                break;
            }
        }
        nutritionistRepositoryImp.updateNutritionist(nutritionist.getNutritionistId(),
                nutritionist.getNutritionistName(), nutritionist.getNutritionistEmail(),
                nutritionist.getNutritionistPassword(), nutritionist.getEducationDiploma(),
                nutritionist.getCustomers());
    }
}
