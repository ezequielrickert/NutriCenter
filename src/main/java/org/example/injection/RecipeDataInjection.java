package org.example.injection;

import org.example.model.recipe.Allergy;
import org.example.model.recipe.Category;
import org.example.model.recipe.Ingredient;
import org.example.model.recipe.Recipe;
import org.example.model.roles.Nutritionist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Arrays;
import java.util.List;

public class RecipeDataInjection {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("UserPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Retrieve Category instances from the database
            Category vegetarian = em.createQuery("SELECT c FROM CATEGORY c WHERE c.categoryName = :name", Category.class)
                    .setParameter("name", "Vegetarian")
                    .getSingleResult();
            Category vegan = em.createQuery("SELECT c FROM CATEGORY c WHERE c.categoryName = :name", Category.class)
                    .setParameter("name", "Vegan")
                    .getSingleResult();
            Category glutenFree = em.createQuery("SELECT c FROM CATEGORY c WHERE c.categoryName = :name", Category.class)
                    .setParameter("name", "Gluten-Free")
                    .getSingleResult();
            Category omnivore = em.createQuery("SELECT c FROM CATEGORY c WHERE c.categoryName = :name", Category.class)
                    .setParameter("name", "Omnivore")
                    .getSingleResult();

            // Retrieve Ingredient instances from the database
            Ingredient almond = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Almond")
                    .getSingleResult();
            Ingredient salmon = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Salmon")
                    .getSingleResult();
            Ingredient milk = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Milk")
                    .getSingleResult();
            Ingredient peanutButter = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Peanut Butter")
                    .getSingleResult();
            Ingredient soySauce = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Soy Sauce")
                    .getSingleResult();
            Ingredient egg = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Egg")
                    .getSingleResult();
            Ingredient shrimp = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Shrimp")
                    .getSingleResult();
            Ingredient bread = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Bread")
                    .getSingleResult();
            Ingredient avocado = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Avocado")
                    .getSingleResult();
            Ingredient chickenBreast = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Chicken Breast")
                    .getSingleResult();
            Ingredient broccoli = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Broccoli")
                    .getSingleResult();
            Ingredient cheddarCheese = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Cheddar Cheese")
                    .getSingleResult();
            Ingredient tuna = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Tuna")
                    .getSingleResult();
            Ingredient apple = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Apple")
                    .getSingleResult();
            Ingredient quinoa = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Quinoa")
                    .getSingleResult();
            Ingredient yogurt = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Yogurt")
                    .getSingleResult();
            Ingredient cashew = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Cashew")
                    .getSingleResult();
            Ingredient lentils = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Lentils")
                    .getSingleResult();
            Ingredient oats = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Oats")
                    .getSingleResult();
            Ingredient carrot = em.createQuery("SELECT i FROM INGREDIENT i WHERE i.ingredientName = :name", Ingredient.class)
                    .setParameter("name", "Carrot")
                    .getSingleResult();

            // Retrieve Nutritionist instances from the database
            Nutritionist nutritionist1 = em.createQuery("SELECT n FROM NUTRITIONIST n WHERE n.nutritionistEmail = :email", Nutritionist.class)
                    .setParameter("email", "lisa.ray@example.com")
                    .getSingleResult();
            Nutritionist nutritionist2 = em.createQuery("SELECT n FROM NUTRITIONIST n WHERE n.nutritionistEmail = :email", Nutritionist.class)
                    .setParameter("email", "mark.neal@example.com")
                    .getSingleResult();
            Nutritionist nutritionist3 = em.createQuery("SELECT n FROM NUTRITIONIST n WHERE n.nutritionistEmail = :email", Nutritionist.class)
                    .setParameter("email", "nancy.drew@example.com")
                    .getSingleResult();
            Nutritionist nutritionist4 = em.createQuery("SELECT n FROM NUTRITIONIST n WHERE n.nutritionistEmail = :email", Nutritionist.class)
                    .setParameter("email", "oscar.wilde@example.com")
                    .getSingleResult();
            Nutritionist nutritionist5 = em.createQuery("SELECT n FROM NUTRITIONIST n WHERE n.nutritionistEmail = :email", Nutritionist.class)
                    .setParameter("email", "pam.beesly@example.com")
                    .getSingleResult();

            // Create and persist Recipe instances
            Recipe recipe1 = new Recipe();
            recipe1.setRecipeName("Avocado Salad");
            recipe1.setRecipeDescription("A healthy and delicious avocado salad.");
            recipe1.setCategoryList(Arrays.asList(vegetarian, vegan, glutenFree));
            recipe1.setIngredientList(Arrays.asList(avocado, broccoli, carrot));
            recipe1.setRecipeUsername(nutritionist1.getNutritionistName());
            recipe1.setIsPublic(true);
            em.persist(recipe1);

            Recipe recipe2 = new Recipe();
            recipe2.setRecipeName("Chicken Broccoli Stir-fry");
            recipe2.setRecipeDescription("A savory stir-fry with chicken and broccoli.");
            recipe2.setCategoryList(Arrays.asList(omnivore, glutenFree));
            recipe2.setIngredientList(Arrays.asList(chickenBreast, broccoli, soySauce));
            recipe2.setRecipeUsername(nutritionist2.getNutritionistName());
            recipe2.setIsPublic(true);
            em.persist(recipe2);

            Recipe recipe3 = new Recipe();
            recipe3.setRecipeName("Peanut Butter Banana Smoothie");
            recipe3.setRecipeDescription("A creamy smoothie with peanut butter and banana.");
            recipe3.setCategoryList(Arrays.asList(vegetarian));
            recipe3.setIngredientList(Arrays.asList(peanutButter, milk));
            recipe3.setRecipeUsername(nutritionist3.getNutritionistName());
            recipe3.setIsPublic(true);
            em.persist(recipe3);

            Recipe recipe4 = new Recipe();
            recipe4.setRecipeName("Quinoa Salad");
            recipe4.setRecipeDescription("A nutritious salad with quinoa and vegetables.");
            recipe4.setCategoryList(Arrays.asList(vegetarian, vegan, glutenFree));
            recipe4.setIngredientList(Arrays.asList(quinoa, avocado, broccoli));
            recipe4.setRecipeUsername(nutritionist4.getNutritionistName());
            recipe4.setIsPublic(true);
            em.persist(recipe4);

            Recipe recipe5 = new Recipe();
            recipe5.setRecipeName("Salmon with Almonds");
            recipe5.setRecipeDescription("A delicious salmon dish topped with almonds.");
            recipe5.setCategoryList(Arrays.asList(omnivore, glutenFree));
            recipe5.setIngredientList(Arrays.asList(salmon, almond, broccoli));
            recipe5.setRecipeUsername(nutritionist5.getNutritionistName());
            recipe5.setIsPublic(true);
            em.persist(recipe5);

            // Create and persist private Recipe instances
            Recipe privateRecipe1 = new Recipe();
            privateRecipe1.setRecipeName("Secret Tuna Sandwich");
            privateRecipe1.setRecipeDescription("A secret recipe for a delicious tuna sandwich.");
            privateRecipe1.setCategoryList(Arrays.asList(omnivore));
            privateRecipe1.setIngredientList(Arrays.asList(tuna, bread, cheddarCheese));
            privateRecipe1.setRecipeUsername(nutritionist1.getNutritionistName());
            privateRecipe1.setIsPublic(false); // Not public
            em.persist(privateRecipe1);

            Recipe privateRecipe2 = new Recipe();
            privateRecipe2.setRecipeName("Special Oatmeal");
            privateRecipe2.setRecipeDescription("A special recipe for hearty oatmeal.");
            privateRecipe2.setCategoryList(Arrays.asList(vegetarian));
            privateRecipe2.setIngredientList(Arrays.asList(oats, milk, apple));
            privateRecipe2.setRecipeUsername(nutritionist2.getNutritionistName());
            privateRecipe2.setIsPublic(false); // Not public
            em.persist(privateRecipe2);

            // Commit the transaction
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
            emf.close();
        }
    }
}
