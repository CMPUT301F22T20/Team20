package com.example.foodtracker.model.mealplan;

import com.example.foodtracker.model.Document;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.model.recipe.SimpleIngredient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MealPlanDay extends Document implements Serializable {

    public static final String MEALPLAN_COLLECTION_NAME = "MealPlan";
    private String day;
    private ArrayList<SimpleIngredient> ingredients;
    private ArrayList<Recipe> recipes;

    public MealPlanDay() {
        day = "";
        ingredients = new ArrayList<>();
        recipes = new ArrayList<>();
    }

    public MealPlanDay(String day, ArrayList<SimpleIngredient> ingredients, ArrayList<Recipe> recipes) {
        this.day = day;
        this.ingredients = ingredients;
        this.recipes = recipes;
    }

    @Override
    public String getCollectionName() {
        return MEALPLAN_COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put(MealPlanDay.FieldName.DAY, this.getDay());
        data.put(MealPlanDay.FieldName.INGREDIENTS, this.getIngredients());
        data.put(MealPlanDay.FieldName.RECIPES, this.getRecipes());
        return data;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<SimpleIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<SimpleIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Represents the field names in the recipes class
     */
    public static class FieldName {
        public static String DAY = "day";
        public static String INGREDIENTS = "ingredients";
        public static String RECIPES = "recipes";
    }
}
