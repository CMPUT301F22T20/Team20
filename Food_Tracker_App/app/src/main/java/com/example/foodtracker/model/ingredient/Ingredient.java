package com.example.foodtracker.model.ingredient;


import com.example.foodtracker.model.Document;
import com.example.foodtracker.model.DocumentableFieldName;
import com.example.foodtracker.model.IngredientUnit.IngredientAmount;
import com.example.foodtracker.model.IngredientUnit.IngredientUnit;
import com.example.foodtracker.model.recipe.SimpleIngredient;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an ingredient in our storage
 * {@link Document} allows objects to be used as Firebase instances
 */
public class Ingredient extends Document {

    /**
     * Represents the collection name in Firebase
     */
    public static String INGREDIENTS_COLLECTION_NAME = "Ingredients-V1.0.0";
    /**
     * Represents the amount we have of this ingredient
     */
    private final IngredientAmount ingredientAmount = new IngredientAmount();
    /**
     * The name of the ingredient
     */
    private String description;
    /**
     * Represents where this ingredient is stored
     */
    private Location location = new Location();

    /**
     * Represents what category we lump this ingredient into, i.e. Fruit
     */
    private Category category = new Category();

    /**
     * Represents the expiry date of our ingredient
     */
    private String expiry;

    public Ingredient() {
    }

    public Ingredient(SimpleIngredient simpleIngredient) {
        setDescription(simpleIngredient.getDescription());
        setUnit(simpleIngredient.getUnit());
        setAmount(simpleIngredient.getAmountQuantity());
        setCategory(simpleIngredient.getCategoryName());
    }

    @Override
    public String getCollectionName() {
        return INGREDIENTS_COLLECTION_NAME;
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put(FieldName.DESCRIPTION.getName(), this.getDescription());
        data.put(FieldName.UNIT.getName(), this.getUnit());
        data.put(FieldName.LOCATION.getName(), this.getLocation());
        data.put(FieldName.CATEGORY.getName(), this.getCategoryName());
        data.put(FieldName.AMOUNT.getName(), this.getAmountQuantity());
        data.put(FieldName.EXPIRY.getName(), this.getExpiry());
        return data;
    }

    public IngredientAmount getAmount() {
        return this.ingredientAmount;
    }

    public double getAmountQuantity() {
        return ingredientAmount.getAmount();
    }

    public void setAmount(double amount) {
        this.ingredientAmount.setAmount(amount);
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = new Category(category);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location.getName();
    }

    public void setLocation(String location) {
        this.location = new Location(location);
    }

    public String getUnit() {
        return this.ingredientAmount.getUnit().toString();
    }

    public void setUnit(String unit) {
        this.ingredientAmount.setUnit(IngredientUnit.valueOf(unit));
    }

    public String getUnitAbbreviation() {
        return this.ingredientAmount.getUnit().getUnitAbbreviation();
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    /**
     * Contains the names of the ingredient class fields
     */
    public enum FieldName implements DocumentableFieldName {
        DESCRIPTION("description", true),
        UNIT("unit", false),
        LOCATION("location", true),
        CATEGORY("category", true),
        AMOUNT("amount", false),
        EXPIRY("expiry", true);

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
}
