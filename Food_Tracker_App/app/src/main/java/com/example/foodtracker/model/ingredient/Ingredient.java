package com.example.foodtracker.model.ingredient;


import com.example.foodtracker.model.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class creates an object to represent an Ingredient with:
 * Description of the Ingredient
 * Cost of the Ingredient
 * Location of the Ingredient
 * Category of the Ingredient
 * Amount of the Ingredient
 * Expiry Date of the Ingredient
 * {@link Document} allows objects to be used as Firebase instances
 */
public class Ingredient extends Document {

    public static String INGREDIENTS_COLLECTION_NAME = "Ingredients";
    /**
     * This variable is private and holds the description for an ingredient of type {@link String}
     */
    private String description;
    /**
     * This variable is private and holds the cost for an ingredient of type {@link Double}
     */
    private String unit;
    /**
     * This variable is private and holds the location for an ingredient of type {@link String}
     */
    private String location;
    /**
     * This variable is private and holds the category for an ingredient of type {@link String}
     */
    private String category;
    /**
     * This variable is private and holds the amount for an ingredient of type {@link Integer}
     */
    private int amount;
    /**
     * This variable is private and holds the expiry for an ingredient of type {@link String}
     */
    private String expiry;

    /**
     * This is an empty constructor to instantiate an {@link Ingredient} object
     */
    public Ingredient() {
    }

    /**
     * This is a constructor to instantiate a {@link Ingredient} object
     *
     * @param description is the description of the Ingredient which is of type {@link String}
     */
    public Ingredient(String description, String unit, String location, String category,
                      int amount, String expiry) {
        setDescription(description);
        setUnit(unit);
        setLocation(location);
        setCategory(category);
        setAmount(amount);
        setExpiry(expiry);
    }

    /**
     * This function returns the collection name for the class of ingredients
     *
     * @return This is the name of the collection of ingredients of type {@link String}
     */
    @Override
    public String getCollectionName() {
        return INGREDIENTS_COLLECTION_NAME;
    }

    /**
     * This function returns key value pair of an ingredient as "Description: #descriptionofingredient""
     * This function is the implementation of a function from the interface {@link Document}
     *
     * @return This is the key-value pair of type {@link Map}
     */
    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put(FieldNames.DESCRIPTION, this.getDescription());
        data.put(FieldNames.UNIT, this.getUnit());
        data.put(FieldNames.LOCATION, this.getLocation());
        data.put(FieldNames.CATEGORY, this.getCategory());
        data.put(FieldNames.AMOUNT, this.getAmount());
        data.put(FieldNames.EXPIRY, this.getExpiry());
        return data;
    }

    /**
     * This function returns the amount of the ingredient
     *
     * @return This is the amount which is of type {@link Integer}
     */
    public int getAmount() {
        return amount;
    }

    /**
     * This function sets the amount of the ingredient
     *
     * @param amount This is the amount which is of type {@link Integer}
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * This function returns the category of the ingredient
     *
     * @return This is the category which is of type {@link String}
     */
    public String getCategory() {
        return category;
    }

    /**
     * This function sets the category of the ingredient
     *
     * @param category This is the amount which is of type {@link String}
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * This function returns the description of the ingredient
     *
     * @return This is the description which is of type {@link String}
     */
    public String getDescription() {
        return description;
    }

    /**
     * This function sets the description of the ingredient
     *
     * @param description This is the description which is of type {@link String}
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This function returns the location of the ingredient
     *
     * @return This is the location which is of type {@link String}
     */
    public String getLocation() {
        return location;
    }

    /**
     * This function sets the location of the ingredient
     *
     * @param location This is the location which is of type {@link String}
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This function returns the cost of the ingredient
     *
     * @return This is the cost which is of type {@link Double}
     */
    public String getUnit() {
        return unit;
    }

    /**
     * This function sets the cost of the ingredient
     *
     * @param unit This is the cost which is of type {@link String}
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * This function returns the expiry date of the ingredient
     *
     * @return This is the expiry date which is of type {@link String}
     */
    public String getExpiry() {
        return expiry;
    }

    /**
     * This function sets the expiry of the ingredient
     *
     * @param expiry This is the expiry which is of type {@link String}
     */
    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    /**
     * This is a public static class that contains all the FieldNames that will be used in EditText later
     * The FieldNames are of type {@link String} and are public and static
     */
    public static class FieldNames {
        public static String DESCRIPTION = "description";
        public static String UNIT = "unit";
        public static String LOCATION = "location";
        public static String CATEGORY = "category";
        public static String AMOUNT = "amount";
        public static String EXPIRY = "expiry";
    }

}
