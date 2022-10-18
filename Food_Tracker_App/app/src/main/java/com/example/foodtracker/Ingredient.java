package com.example.foodtracker;

public class Ingredient {
    private String description;
    private int quantity;
    private String location;
    private String category;
    private int amount;
    private String expiry;

    public Ingredient(String description, int quantity, String location, String category, int amount, String expiry){
        this.description = description;
        this.quantity = quantity;
        this.location = location;
        this.category = category;
        this.amount = amount;
        this.expiry = expiry;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
