package com.example.foodtracker.model.recipe;

import com.example.foodtracker.model.IngredientUnit.IngredientAmount;
import com.example.foodtracker.model.IngredientUnit.IngredientUnit;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.utils.ConversionUtil;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents basic information about an ingredient
 */
public class SimpleIngredient implements Serializable {

    public SimpleIngredient() {}

    public SimpleIngredient(Ingredient ingredient) {
        setIngredientAmount(ingredient.getAmount());
        setDescription(ingredient.getDescription());
        this.category = ingredient.getCategory();
    }

                            @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleIngredient that = (SimpleIngredient) o;
        return getDescription().equalsIgnoreCase(that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription());
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
    private IngredientAmount amount = new IngredientAmount();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return this.category.getName();
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = new Category(category);
    }

    public double getAmountQuantity() {
        return amount.getAmount();
    }

    public IngredientAmount getAmount() {
        return amount;
    }

    public void setIngredientAmount(IngredientAmount amount) {
        this.amount = amount;
    }

    public void addIngredientAmount(IngredientAmount toAdd) {
        IngredientAmount converted = ConversionUtil.convertAmount(toAdd, this.amount.getUnit());
        setAmount(getAmountQuantity() + converted.getAmount());
    }
    public void setAmount(double amount) {
        this.amount.setAmount(amount);
    }

    public String getUnit() {
        return amount.getUnit().name();
    }

    public String getUnitAbbreviation() {
        return amount.getUnit().getUnitAbbreviation();
    }

    public void setUnit(String unit) {
        this.amount.setUnit(IngredientUnit.valueOf(unit));
    }
}
