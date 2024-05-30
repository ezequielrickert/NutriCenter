package org.example.service;


import org.example.model.history.CustomerHistory;
import org.example.model.history.WeightHistory;
import org.example.model.recipe.Ingredient;
import org.example.model.roles.Customer;
import org.example.model.roles.Nutritionist;
import org.example.model.roles.Store;
import org.example.repository.customer.CostumerRepository;
import org.example.repository.customer.CostumerRepositoryImp;
import org.example.repository.customerhistory.CustomerHistoryRepository;
import org.example.repository.customerhistory.CustomerHistoryRepositoryImplementation;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;


public class CustomerService {

    private final CostumerRepository customerRepository;
    private final CustomerHistoryRepository customerHistoryRepository;

    public CustomerService(EntityManager entityManager) {
        this.customerRepository = new CostumerRepositoryImp(entityManager);
        this.customerHistoryRepository = new CustomerHistoryRepositoryImplementation(entityManager);
    }

    public void createUser(String username, String email, String password) {
        // List<Day> dayList = createDays();
        WeightHistory weightHistory = new WeightHistory();
        CustomerHistory customerHistory = customerHistoryRepository.createCustomerHistory();
        customerRepository.createUser(username, email, password, customerHistory);
    }

    public Customer readUser(Long inClientId) {
        return customerRepository.readUser(inClientId);
    }

    public void deleteUser(Long clientId) {
        customerRepository.deleteUser(clientId);
    }

    public Customer getCustomerByName(String username){
        return customerRepository.fetchCustomerByUsername(username);
    }

    public void subscribe(Nutritionist nutritionist, Customer customer) {
        customer.getNutritionists().add(nutritionist);
        customerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCustomerEmail(), customer.getNutritionists(), customer.getStores(),
                customer.getIngredients(), customer.getWeightHistory());
    }

    public void unsubscribe(String nutritionist, Customer customer) {
        List<Nutritionist> nutritionistList = customer.getNutritionists();
        for (Nutritionist nutritionist1 : nutritionistList) {
            if (nutritionist1.getNutritionistName().equals(nutritionist)) {
                nutritionistList.remove(nutritionist1);
                customer.setNutritionists(nutritionistList);
                break;
            }
        }
        customerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCustomerEmail(), nutritionistList, customer.getStores(),
                customer.getIngredients(), customer.getWeightHistory());
    }

    public Boolean isSubscribed(String nutritionistName, Customer customer) {
        List<Nutritionist> nutritionistList = customer.getNutritionists();
        for (Nutritionist nutritionist : nutritionistList) {
            if (nutritionist.getNutritionistName().equals(nutritionistName)) {
                return true;
            }
        }
        return false;
    }

    public void followStore(Store store, Customer customer) {
        customer.getStores().add(store);
        customerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCustomerEmail(), customer.getNutritionists(), customer.getStores(),
                customer.getIngredients(), customer.getWeightHistory());

    }

    public void unfollowStore(Store store, Customer customer) {
        List<Store> stores = customer.getStores();
        stores.remove(store);
        customerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCustomerEmail(), customer.getNutritionists(), customer.getStores(),
                customer.getIngredients(), customer.getWeightHistory());
    }

    public Boolean followsStore(Store store, Customer customer) {
        List<Store> stores = customer.getStores();
        for (Store persistedStores : stores) {
            if (Objects.equals(store.getStoreId(), persistedStores.getStoreId())) {
                return true;
            }
        }
        return false;
    }

    public void followIngredient(Ingredient ingredient, Customer customer) {
        customer.getIngredients().add(ingredient);
        customerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCustomerEmail(), customer.getNutritionists(), customer.getStores(),
                customer.getIngredients(), customer.getWeightHistory());
    }

    public void unfollowIngredient(Ingredient ingredient, Customer customer) {
        List<Ingredient> ingredients = customer.getIngredients();
        ingredients.remove(ingredient);
        customerRepository.updateUser(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCustomerEmail(), customer.getNutritionists(), customer.getStores(),
                customer.getIngredients(), customer.getWeightHistory());
    }

    public Boolean followsIngredient(Ingredient ingredient, Customer customer) {
        List<Ingredient> ingredients = customer.getIngredients();
        for (Ingredient persistedIngredients : ingredients) {
            if (Objects.equals(ingredient.getIngredientId(), persistedIngredients.getIngredientId())) {
                return true;
            }
        }
        return false;
    }


}

