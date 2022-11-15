package com.example.foodtracker.model.recipe;

import com.example.foodtracker.model.Document;
import com.example.foodtracker.model.DocumentableFieldName;
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
        data.put(Recipe.FieldName.TITLE.getName(), this.getTitle());
        data.put(Recipe.FieldName.IMAGE.getName(), this.getImage());
        data.put(Recipe.FieldName.PREPARATION_TIME.getName(), this.getPrepTime());
        data.put(Recipe.FieldName.CATEGORY.getName(), this.getCategory());
        data.put(Recipe.FieldName.SERVINGS.getName(), this.getServings());
        data.put(Recipe.FieldName.COMMENT.getName(), this.getComment());
        data.put(FieldName.INGREDIENTS.getName(), this.getIngredients());
        return data;
    }

    /**
     * Represents the field names in the recipes class
     */
    public enum FieldName implements DocumentableFieldName {
        TITLE("title", true),
        IMAGE("image", false),
        PREPARATION_TIME("prepTime", true),
        CATEGORY("category", true),
        SERVINGS("servings", false),
        COMMENT("comment", false),
        INGREDIENTS("ingredients", false);

        private final String name;
        private final boolean sortable;

        FieldName(String fieldName, boolean sortable) {
            this.name = fieldName;
            this.sortable = sortable;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean sortable() {
            return sortable;
        }
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
