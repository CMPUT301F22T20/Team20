package com.example.foodtracker.model.recipe;

import com.example.foodtracker.model.IngredientUnit.IngredientUnit;
import com.example.foodtracker.model.ingredient.Category;

import java.io.Serializable;

/**
 * Represents basic information about an ingredient
 *
 * @implNote the getters and setters in this class are used when mapping this class from firestore
 */
public class SimpleIngredient implements Serializable {

    public SimpleIngredient() {}

    /**
     * A brief description of what the ingredient is
     */
    private String description;

    /**
     * The category that represents this ingredient (i.e. Fruit)
     */
    private com.example.foodtracker.model.ingredient.Category category;

    /**
     * The amount that we have of this ingredient
     */
    private int amount;

    private IngredientUnit unit;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category.getName();
    }

    public void setCategory(String category) {
        this.category = new Category(category);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit.name();
    }

    public String getUnitAbbreviation() {
        return unit.getUnitAbbreviation();
    }

    public void setUnit(String unit) {
        this.unit = IngredientUnit.valueOf(unit);
    }
}
