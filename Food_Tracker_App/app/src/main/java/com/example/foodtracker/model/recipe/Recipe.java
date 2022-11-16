package com.example.foodtracker.model.recipe;

import com.example.foodtracker.model.Document;
import com.example.foodtracker.model.ingredient.Ingredient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipe extends Document implements Serializable {

    public static final String RECIPES_COLLECTION_NAME = "Recipes";
    private String image; // TODO: figure out if this should be a String or a different data type
    private String title;
    private int prepTime;
    private int servings;
    private String category;
    private String comment;
    private ArrayList<Ingredient> ingredients;

    public Recipe() {
        image = "";
        title = "";
        prepTime = 0;
        servings = 0;
        category = "";
        comment = "";
        ingredients = new ArrayList<>();
    }

    public Recipe(String image, String title, int prepTime, int servings, String category, String comment, ArrayList<Ingredient> ingredients) {
        this.image = image;
        this.title = title;
        this.prepTime = prepTime;
        this.servings = servings;
        this.category = category;
        this.comment = comment;
        this.ingredients = ingredients;
    }


    @Override
    public String getCollectionName() {
        return RECIPES_COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put(Recipe.FieldNames.TITLE, this.getTitle());
        data.put(Recipe.FieldNames.IMAGE, this.getImage());
        data.put(Recipe.FieldNames.PREPTIME, this.getPrepTime());
        data.put(Recipe.FieldNames.CATEGORY, this.getCategory());
        data.put(Recipe.FieldNames.SERVINGS, this.getServings());
        data.put(Recipe.FieldNames.COMMENT, this.getComment());
        data.put(FieldNames.INGREDIENTS, this.getIngredients());
        return data;
    }

    public static class FieldNames {
        public static String TITLE = "title";
        public static String IMAGE = "image";
        public static String PREPTIME = "prepTime";
        public static String CATEGORY = "category";
        public static String SERVINGS = "servings";
        public static String COMMENT = "comment";
        public static String INGREDIENTS = "ingredients";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
