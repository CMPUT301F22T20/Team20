package com.example.foodtracker.ui.mealPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealPlan.MealPlanDay;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {

    ListView recipeListView;
    ArrayList<Recipe> recipeArrayList;
    ArrayAdapter<Recipe> adapter;
    MealPlanDay mealPlan;
    /**
     * This is a private final variable
     * This holds a collection of {@link Recipe} objects and is of type {@link Recipe}
     */
    private final Collection<Recipe> recipesCollection = new Collection<>(Recipe.class, new Recipe());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
    }
}