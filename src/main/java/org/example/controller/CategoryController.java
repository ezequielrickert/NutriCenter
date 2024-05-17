package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.recipe.Category;
import org.example.service.CategoryService;
import spark.Spark;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.example.Application.gson;

public class CategoryController {

    public void run() {

        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UserPU");
        final CategoryService categoryService = new CategoryService(entityManagerFactory.createEntityManager());

        Spark.get("/categories", (req, res) -> {
            List<Category> categories = categoryService.getCategoriesOrderedByName();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.toJson(categories);
        }, gson::toJson);
    }

}
