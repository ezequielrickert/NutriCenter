package org.example.injection;

public class InjectionRunner {

    public void run() {
        // Instantiate and run data injection classes in the desired order
        UserDataInjection.main(null);
        CategoryAndAllergyDataInjection.main(null);
        IngredientDataInjection.main(null);
        RecipeDataInjection.main(null);
        CustomerHistoryDataInjection.main(null);
        StockDataInjection.main(null);
        WeightDataInjection.main(null);
    }

    public static void main(String[] args) {
        InjectionRunner runner = new InjectionRunner();
        runner.run();
    }
}