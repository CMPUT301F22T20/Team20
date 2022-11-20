package com.example.foodtracker.model.ingredient;

import java.io.Serializable;

/**
 * Represents basic information about an ingredient
 *
 * @implNote the getters and setters in this class are used when mapping this class from firestore
 */
public class SimpleIngredient implements Serializable {

    public SimpleIngredient() {}

    public SimpleIngredient(String description, String category, int amount, String unit) {
        this.setDescription(description);
        this.setCategory(category);
        this.setAmount(amount);
        this.setUnit(unit);
    }

    /**
     * A brief description of what the ingredient is
     */
    private String description;

    /**
     * The category that represents this ingredient (i.e. Fruit)
     */
    private Category category;

    /**
     * The amount that we have of this ingredient
     */
    private int amount;

    /**
     * The unit of the amount that we carry (i.e. {@link SimpleIngredient#amount} is 2, and unit is kg)
     */
    private String unit;

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
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
