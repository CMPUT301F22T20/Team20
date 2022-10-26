package com.example.foodtracker.model;


import java.util.HashMap;
import java.util.Map;

public class Ingredient implements Documentable {

    public static String INGREDIENTS_COLLECTION_NAME = "Ingredients";

    public static class FieldNames {
        public static String DESCRIPTION = "description";
        public static String COST = "cost";
        public static String LOCATION = "location";
        public static String CATEGORY = "category";
        public static String AMOUNT = "amount";
        public static String EXPIRY = "expiry";
    }

    private String description;
    private Double cost;
    private String location;
    private String category;
    private int amount;
    private String expiry;

    public Ingredient() {}

    public Ingredient(String description) {
        this.description = description;
    }

    @Override
    public String getCollectionName() {
        return INGREDIENTS_COLLECTION_NAME;
    }

    @Override
    public String getKey() {
        return getDescription();
    }

    @Override
    public Map<String, Object> getData() {
        Map<String, Object> data = new HashMap<>();
        data.put(FieldNames.DESCRIPTION, this.getDescription());
        data.put(FieldNames.COST, this.getCost());
        data.put(FieldNames.LOCATION, this.getLocation());
        data.put(FieldNames.CATEGORY, this.getCategory());
        data.put(FieldNames.AMOUNT, this.getAmount());
        data.put(FieldNames.EXPIRY, this.getExpiry());
        return data;
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
}
