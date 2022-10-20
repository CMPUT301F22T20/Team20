package com.example.foodtracker.ui.ingredients;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class represents an object of "Ingredient" type. It contains a description {@link String},
 * the quantity {@link Integer}, its location {@link String}, category {@link String}, its cost {@link BigDecimal}
 * and its expiry date {@link Date}
 *
 * This represents the ingredients we have in storage
 */

public class Ingredient {
    private String description;
    private BigDecimal cost;
    private String location;
    private String category;
    private int amount;
    private Date expiry;
    private boolean expanded;

    /**
     * A constructor to create an Ingredient object
     * @param description
     * @param amount
     * @param location
     * @param category
     * @param amount
     * @param expiry
     */
    public Ingredient(String description,int amount, String location, String category, BigDecimal cost, Date expiry) {

        setDescription(description);
        setAmount(amount);
        setLocation(location);
        setCategory(category);
        setAmount(amount);
        setExpiry(expiry);

        this.expanded = false;
    }

    /**
     * a getter that returns the cost of an ingredient
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * setter that sets the cost of an ingredient
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * getter that retrieves value of an ingredient category
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * setter that sets value of category
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * getter that gets value of ingredient description/name
     * @return description (the name of the ingredient)
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter that sets the value of the ingredient description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter that gets the value of the ingredient location
     * @return location (the location that the ingredient is stored in)
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter that sets the location of the ingredient
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter that gets the available amount of the ingredient
     * @return quantity (how much of the ingredient we have that is available)
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * setter that sets how much is available (for a given ingredient)
     * @param cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * getter that gets the expiry date of an ingredient
     * @return expiry (expiry date of ingredient)
     */
    public Date getExpiry() {
        return expiry;
    }

    /**
     * setter that sets expiry date of ingredient
     * @param expiry
     */
    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
