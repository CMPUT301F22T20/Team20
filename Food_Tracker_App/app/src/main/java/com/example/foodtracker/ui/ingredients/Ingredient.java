package com.example.foodtracker.ui.ingredients;

public class Ingredient {
    private String description;
    private Double cost;
    private String location;
    private String category;
    private int amount;
    private String expiry;

    boolean visible; //to check if ingredient is expanded

    public Ingredient(String description, Double cost, String location, String category,
                      int amount, String expiry) {
        setDescription(description);
        setCost(cost);
        setLocation(location);
        setCategory(category);
        setAmount(amount);
        setExpiry(expiry);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
