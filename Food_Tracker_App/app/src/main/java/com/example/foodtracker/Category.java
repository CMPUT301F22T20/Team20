package com.example.foodtracker;

import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * category for ingredients
 * returns the categories by getCategories
 */
public enum Category {

    CATEGORY1("Mock");

    private static final List<String> CATREGORYSTRINGS;
    private static final List<Category> CATEGORIES;
    private final String categoryValue;


    static {
        //List of Category String values
        CATREGORYSTRINGS = new ArrayList<>();
        for (Category category : Category.values()) {
            CATREGORYSTRINGS.add(category.categoryValue);
        }
        CATEGORIES = new ArrayList<Category>(EnumSet.allOf(Category.class));
    }

    Category(String value) {
        this.categoryValue = value;
    }

    /**
     *
     * @return list contains Category String values
     */
    public static List<String> getCatregorystrings() {
        return Collections.unmodifiableList(CATREGORYSTRINGS);
    }

    /**
     *
     * @return list contains Categories
     */
    public static List<Category> getCategories(){
        return Collections.unmodifiableList(CATEGORIES);
    }

    /**
     *
     * @param category
     * @return  String value of location
     */
    public static String getValue(Category category){
        return category.categoryValue;
    }

    /**
     *checks if location exists
     * @param toBeChecked
     * @return true if exists
     */
    public static boolean checkCategory(String toBeChecked){
        for (String category : CATREGORYSTRINGS){
            if (toBeChecked.equals(category))
                return true;
        }

        return false;
    }

    /**
     * delete category
     */
    public static void deleteCategory(String toBeDeleted) throws IllegalArgumentException{
        if (checkCategory(toBeDeleted))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                CATEGORIES.removeIf(p -> Category.getValue(p).equals(toBeDeleted));
                CATREGORYSTRINGS.removeIf(q -> q.equals(toBeDeleted));
            }
            else{
                throw new IllegalArgumentException("Deletion not successful");
            }
    }
}