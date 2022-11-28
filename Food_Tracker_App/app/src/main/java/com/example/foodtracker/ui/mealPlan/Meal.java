package com.example.foodtracker.ui.mealPlan;

public class Meal {
    String mealName;
    String mealType;
    int mealNumber;
    int mealServings;

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public int getMealNumber() {
        return mealNumber;
    }

    public void setMealNumber(int mealNumber) {
        this.mealNumber = mealNumber;
    }

    public Meal(String mealName, int mealServings) {
        this.mealName = mealName;
        this.mealServings = mealServings;

    }

    public int getMealServings() {
        return mealServings;
    }

    public void setMealServings(int mealServings) {
        this.mealServings = mealServings;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }


}
