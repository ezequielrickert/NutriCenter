package org.example;

import com.google.common.base.Strings;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.example.controller.*;
import org.example.model.*;
import org.example.model.login.LoginData;
import spark.Spark;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class Application {

  private static final Gson gson = new Gson();


    public static void main(String[] args) {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");

//    createAllergies(entityManagerFactory.createEntityManager());

    Spark.port(8080);

      Spark.options("/*", (request, response) -> {

          String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
          if (accessControlRequestHeaders != null) {
              response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
          }

          String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
          if (accessControlRequestMethod != null) {
              response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
          }

          return "OK";
      });

      Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

      // Post to create Customer
      Spark.post("/createCustomer", (req, res) -> {
          String body = req.body();
          Customer newCustomer = gson.fromJson(body, Customer.class);
          String username = newCustomer.getCustomerName();
          String email = newCustomer.getCustomerEmail();
          String password = newCustomer.getCustomerPassword();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          CustomerController customerController = new CustomerController(entityManager);
          customerController.createClient(username, email, password);
          return newCustomer;
      }, gson::toJson);


        Spark.post("/authenticateUser", (req, res) -> {
            String body = req.body();
            LoginData loginData = gson.fromJson(body, LoginData.class);
            String username = loginData.getUsername();
            String password = loginData.getPassword();


            EntityManager entityManager = entityManagerFactory.createEntityManager();
            LoginController loginController = new LoginController(entityManager);
            //returns the actual password of the user with the given username or null if the user is not found
            String userPassword = loginController.fetchUser(username);


            if (userPassword != null && userPassword.equals(password)) {
                // if the user is found and the password matches, return a token
                String token = UUID.randomUUID().toString();;
                return token;
            } else {
                // if the username is not found or the password does not match, return an error message
                res.status(401);
                return "Username or password is incorrect";
            }
        }, gson::toJson);

      // Post to create SuperAdmin
      Spark.post("/createSuperAdmin", (req, res) -> {
         String body = req.body();
         SuperAdmin superAdmin = gson.fromJson(body, SuperAdmin.class);
         String username = superAdmin.getAdminUsername();
         String password = superAdmin.getAdminPassword();
         EntityManager entityManager = entityManagerFactory.createEntityManager();
         SuperAdminController superAdminController = new SuperAdminController(entityManager);
         superAdminController.createSuperAdmin(username, password);
         return superAdmin;
      } , gson::toJson);

    /* Dynamic Content from Db */
    Spark.get("/persisted-customers/:id",
            (req, resp) -> {
              final String id = req.params("id");

              /* Business Logic */
              final EntityManager entityManager = entityManagerFactory.createEntityManager();
              final EntityTransaction tx = entityManager.getTransaction();
              tx.begin();
              Customer customer = entityManager.find(Customer.class, Long.valueOf(id));
              tx.commit();
              entityManager.close();

              resp.type("application/json");
              return customer.asJson();
            }
    );

    Spark.get("/persisted-nutritionist/:id",
            (req, resp) -> {
              final String id = req.params("id");

              /* Business Logic */
              final EntityManager entityManager = entityManagerFactory.createEntityManager();
              final EntityTransaction tx = entityManager.getTransaction();
              tx.begin();
              Nutritionist nutritionist = entityManager.find(Nutritionist.class, Long.valueOf(id));
              tx.commit();
              entityManager.close();

              resp.type("application/json");
              return nutritionist.asJson();
            }
    );

      // Post to create Nutritionist
      Spark.post("/createNutritionist", (req, res) -> {
          String body = req.body();
          Nutritionist nutritionist = gson.fromJson(body, Nutritionist.class);
          String name = nutritionist.getNutritionistName();
          String mail = nutritionist.getNutritionistEmail();
          String diploma = nutritionist.getEducationDiploma();
          String password = nutritionist.getNutritionistPassword();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          NutritionistController nutritionistController = new NutritionistController(entityManager);
          nutritionistController.createNutritionist(name, mail, password, diploma);
          return nutritionist;
      } , gson::toJson);

      // Post to create Ingredient
      Spark.post("/createIngredient", (req, res) -> {
          String body = req.body();
          Ingredient ingredient = gson.fromJson(body, Ingredient.class);
          String ingredientName = ingredient.getIngredientName();
          Allergy allergy = ingredient.getAllergy();
          int proteins = ingredient.getProteins();
          int sodium = ingredient.getSodium();
          int calories = ingredient.getCalories();
          int totalFat = ingredient.getTotalFat();
          int cholesterol = ingredient.getCholesterol();
          int totalCarbohydrate = ingredient.getTotalCarbohydrate();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          IngredientController ingredientController = new IngredientController(entityManager);
          ingredientController.createIngredient(ingredientName, allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
          return ingredient;
      } , gson::toJson);

      // Gets a list of all ingredients
      Spark.get("/ingredients", (req, res) -> {
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          IngredientController ingredientController = new IngredientController(entityManager);
          List<Ingredient> ingredients = ingredientController.getIngredientsOrderedByName(entityManager);
          System.out.println(ingredients);
          return gson.toJson(ingredients);
      }, gson::toJson);

      //post to update ingredient
      Spark.post("/updateIngredient", (req, res) -> {
          String body = req.body();
          JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
          Ingredient ingredient = gson.fromJson(jsonObject.get("ingredient"), Ingredient.class);
          Allergy allergy = gson.fromJson(jsonObject.get("allergy"), Allergy.class);
          int proteins = gson.fromJson(jsonObject.get("proteins"), Integer.class);
          int sodium = gson.fromJson(jsonObject.get("sodium"), Integer.class);
          int calories = gson.fromJson(jsonObject.get("calories"), Integer.class);
          int totalFat = gson.fromJson(jsonObject.get("totalFat"), Integer.class);
          int cholesterol = gson.fromJson(jsonObject.get("cholesterol"), Integer.class);
          int totalCarbohydrate = gson.fromJson(jsonObject.get("totalCarbohydrate"), Integer.class);
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          IngredientController ingredientController = new IngredientController(entityManager);
          ingredientController.updateIngredient(ingredient.getIngredientId(),
                  allergy, proteins, sodium, calories, totalFat, cholesterol, totalCarbohydrate);
          return ingredient.asJson();
      } , gson::toJson);

      //post to delete ingredient
      Spark.post("/deleteIngredient", (req, res) -> {
          String body = req.body();
          JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
          Ingredient ingredient = gson.fromJson(jsonObject.get("ingredient"), Ingredient.class);
          Long id = ingredient.getIngredientId();
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          IngredientController ingredientController = new IngredientController(entityManager);
          ingredientController.deleteIngredient(id);
          return ingredient;
      } , gson::toJson);

      Spark.get("/allergies", (req, res) -> {
          EntityManager entityManager = entityManagerFactory.createEntityManager();
          AllergyController allergyController = new AllergyController(entityManager);
          List<Allergy> allergies = allergyController.getAllergiesOrderedByName(entityManager);
          System.out.println(allergies);
          Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
          String result = gson.toJson(allergies);
          System.out.println(result);
          return result;
      }, gson::toJson);

      Spark.get("/categories", (req, res) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            CategoryController categoryController = new CategoryController(entityManager);
            List<Category> categories = categoryController.getCategoriesOrderedByName();
            System.out.println(categories);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String result = gson.toJson(categories);
            System.out.println(result);
            return result;
        }, gson::toJson);

        Spark.get("/recipes", (req, res) -> {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            RecipeController recipeController = new RecipeController(entityManager);
            List<Recipe> recipes = recipeController.getRecipesOrderedByName();
            System.out.println(recipes);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String result = gson.toJson(recipes);
            System.out.println(result);
            return result;
        }, gson::toJson);

        Spark.post("/createRecipe", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            String recipeName = gson.fromJson(jsonObject.get("recipeName"), String.class);
            String recipeDescription = gson.fromJson(jsonObject.get("recipeDescription"), String.class);

            Type categoryListType = new TypeToken<List<Category>>() {}.getType();
            List<Category> categoryList = gson.fromJson(jsonObject.get("categoryList"), categoryListType);

            Type ingredientListType = new TypeToken<List<Ingredient>>() {}.getType();
            List<Ingredient> ingredientList = gson.fromJson(jsonObject.get("ingredientList"), ingredientListType);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            RecipeController recipeController = new RecipeController(entityManager);
            recipeController.createRecipe(recipeName, recipeDescription, categoryList, ingredientList);
            return req;
        }, gson::toJson);

        Spark.post("/updateRecipe", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Recipe recipe = gson.fromJson(jsonObject.get("recipe"), Recipe.class);
            String recipeName = gson.fromJson(jsonObject.get("recipeName"), String.class);
            String recipeDescription = gson.fromJson(jsonObject.get("recipeDescription"), String.class);

            Type categoryListType = new TypeToken<List<Category>>() {}.getType();
            List<Category> categoryList = gson.fromJson(jsonObject.get("categoryList"), categoryListType);

            Type ingredientListType = new TypeToken<List<Ingredient>>() {}.getType();
            List<Ingredient> ingredientList = gson.fromJson(jsonObject.get("ingredientList"), ingredientListType);

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            RecipeController recipeController = new RecipeController(entityManager);
            recipeController.updateRecipe(recipe.getRecipeId(), recipeName, recipeDescription, categoryList, ingredientList);
            return recipe;
        } , gson::toJson);

        Spark.post("/deleteRecipe", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Recipe recipe = gson.fromJson(jsonObject.get("recipe"), Recipe.class);
            Long id = recipe.getRecipeId();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            RecipeController recipeController = new RecipeController(entityManager);
            recipeController.deleteRecipe(id);
            return recipe;
        } , gson::toJson);

        // Post to create Store
        Spark.post("/createStore", (req, res) -> {
            String body = req.body();
            Store store = gson.fromJson(body, Store.class);
            String storeName = store.getStoreName();
            String storeMail = store.getStoreMail();
            String storePassword = store.getStorePassword();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            StoreController storeController = new StoreController(entityManager);
            storeController.createStore(storeName, storeMail, storePassword);
            return store;
        } , gson::toJson);

        // Post to update Store
        Spark.post("/updateStore", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Store store = gson.fromJson(jsonObject.get("store"), Store.class);
            String storeName = gson.fromJson(jsonObject.get("storeName"), String.class);
            String storeMail = gson.fromJson(jsonObject.get("storeEmail"), String.class);
            String storePassword = gson.fromJson(jsonObject.get("storePassword"), String.class);
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            StoreController storeController = new StoreController(entityManager);
            storeController.updateStore(store.getStoreId(), storeName, storeMail, storePassword);
            return store.asJson();
        } , gson::toJson);

        // Post to delete Store
        Spark.post("/deleteStore", (req, res) -> {
            String body = req.body();
            JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
            Store store = gson.fromJson(jsonObject.get("store"), Store.class);
            Long id = store.getStoreId();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            StoreController storeController = new StoreController(entityManager);
            storeController.deleteStore(id);
            return store;
        } , gson::toJson);

      Spark.get("/persisted-store/:id", (req, resp) -> {
          final String id = req.params("id");

          /* Business Logic */
          final EntityManager entityManager = entityManagerFactory.createEntityManager();
          final EntityTransaction tx = entityManager.getTransaction();
          tx.begin();
          Store store = entityManager.find(Store.class, Long.valueOf(id));
          tx.commit();
          entityManager.close();

          resp.type("application/json");
          return store.asJson();
        }
    );
  }
    private static void createAllergies(EntityManager entityManager) {
        AllergyController allergyController = new AllergyController(entityManager);
//        allergyController.createAllergy("lactose", "intolerante a la lactosa");
//        allergyController.createAllergy("tacc", "celiaco");
        allergyController.createAllergy("none","Sin alergias");
    }

    private static void createCategories(EntityManager entityManager) {
        CategoryController categoryController = new CategoryController(entityManager);
        categoryController.createCategory("Vegano");
        categoryController.createCategory("Vegetariano");
        categoryController.createCategory("Keto");
        categoryController.createCategory("Paleo");
        categoryController.createCategory("Omnivoro");
    }
}