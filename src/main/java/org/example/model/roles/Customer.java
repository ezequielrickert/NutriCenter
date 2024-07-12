package org.example.model.roles;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import org.example.model.history.CustomerHistory;
import org.example.model.history.WeightHistory;
import org.example.model.recipe.Ingredient;

import javax.persistence.*;
import java.util.List;

@Entity(name = "CUSTOMER")
public class Customer {

    @Id
    @Expose
    @GeneratedValue(generator = "userGen", strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Expose
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerHistoryId", referencedColumnName = "customerHistoryId")
    private CustomerHistory customerHistory;

    @Expose
    @Column(nullable = false, unique = false)
    private String customerName;

    @Expose
    @Column(nullable = false, unique = true)
    private String customerEmail;

    @Column(nullable = false, unique = false)
    private String customerPassword;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "customer_nutritionist",
            joinColumns = @JoinColumn(referencedColumnName = "customerId"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "nutritionistId"))
    private List<Nutritionist> nutritionists;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "customer_store",
            joinColumns = @JoinColumn(referencedColumnName = "customerId"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "storeId"))
    private List<Store> stores;

    @ManyToMany
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<WeightHistory> weightHistory;

    public Customer(String customerName, String customerEmail, String customerPassword) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    public Customer() {

    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public CustomerHistory getCustomerHistory() {
        return customerHistory;
    }

    public void setCustomerHistory(CustomerHistory customerHistory) {
        this.customerHistory = customerHistory;
    }

    public void setCustomerEmail(String mail) {
        this.customerEmail = mail;
    }

    public void setCustomerPassword(String password) { this.customerPassword = password; }

    public Long getCustomerId() {
    return customerId;
  }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPassword() { return customerPassword; }

  public String asJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

    public List<Nutritionist> getNutritionists() {
        return nutritionists;
    }

    public void setNutritionists(List<Nutritionist> nutritionists) {
        this.nutritionists = nutritionists;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<WeightHistory> getWeightHistory() {
        return weightHistory;
    }

    public void setWeightHistory(List<WeightHistory> weightHistory) {
        this.weightHistory = weightHistory;
    }
}
