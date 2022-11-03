package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.ui.NavBar;

public class RecipeDisplay extends AppCompatActivity {

    protected TextView recipeTitle;
    protected TextView recipePrepTime;
    protected TextView recipeServings;
    protected TextView recipeCategory;
    protected TextView recipeComment;
    protected RecyclerView recipeIngredients;
    protected Button deleteRecipeButton;
    protected Button editRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_display);

        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("RECIPE");

        recipeTitle = findViewById(R.id.recipeDisplayTitle);
        recipePrepTime = findViewById(R.id.recipeDisplayPrepTime);
        recipeServings = findViewById(R.id.recipeDisplayServings);
        recipeCategory = findViewById(R.id.recipeDisplayCategory);
        recipeComment = findViewById(R.id.recipeDisplayComment);
        recipeIngredients = findViewById(R.id.recipeDisplayIngredientList);
        deleteRecipeButton = findViewById(R.id.recipeDeleteButton);
        editRecipeButton = findViewById(R.id.recipeEditButton);

        recipeTitle.setText(recipe.getTitle());
        recipePrepTime.setText("Preperation Time: " + String.valueOf(recipe.getPrepTime()));
        recipeServings.setText("Servings: " + String.valueOf(recipe.getServings()));
        recipeCategory.setText("Category: " + recipe.getCategory());
        recipeComment.setText("Comment:\n" + recipe.getComment());

        if (savedInstanceState == null) {
            createNavbar();
        }
    }

    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.RECIPES);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.recipe_display_nav_bar, navBar)
                .commit();
    }
}