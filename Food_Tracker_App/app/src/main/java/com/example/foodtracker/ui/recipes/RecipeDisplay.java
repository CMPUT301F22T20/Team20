package com.example.foodtracker.ui.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;

import java.util.Locale;

public class RecipeDisplay extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_display);

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("RECIPE");

        RecipeIngredientsRecyclerViewAdapter adapter = new RecipeIngredientsRecyclerViewAdapter(this, recipe.getIngredients());

        TextView recipeTitle = findViewById(R.id.recipeDisplayTitle);
        TextView recipePrepTime = findViewById(R.id.recipeDisplayPrepTime);
        TextView recipeServings = findViewById(R.id.recipeDisplayServings);
        TextView recipeCategory = findViewById(R.id.recipeDisplayCategory);
        TextView recipeComment = findViewById(R.id.recipeDisplayComment);
        RecyclerView recipeIngredients = findViewById(R.id.recipeDisplayIngredientList);
        Button deleteRecipeButton = findViewById(R.id.recipeDeleteButton);
        Button editRecipeButton = findViewById(R.id.recipeEditButton);
        ImageView recipeImage = findViewById(R.id.recipe_display_image);

        recipeTitle.setText(recipe.getTitle());
        recipePrepTime.setText(String.format(Locale.CANADA, "Preparation Time: %d", recipe.getPrepTime()));
        recipeServings.setText(String.format(Locale.CANADA, "Servings: %d", recipe.getServings()));
        recipeCategory.setText(String.format(Locale.CANADA, "Category: %s", recipe.getCategory()));
        recipeComment.setText(String.format(Locale.CANADA, "Comment:\n\n%s", recipe.getComment()));

        recipeIngredients.setLayoutManager(new LinearLayoutManager(this));
        recipeIngredients.setAdapter(adapter);

        if (savedInstanceState == null) {
            createNavbar();
            createTopBar();
        }

        editRecipeButton.setOnClickListener(v -> {
//                Intent intent = new Intent(getApplicationContext(), EditRecipeActivity.class);
//                intent.putExtra("EDIT_RECIPE", recipe);
//                startActivity(intent);
        });

        deleteRecipeButton.setOnClickListener(v -> {
            getIntent().putExtra("DELETED_RECIPE", recipe);
            setResult(RESULT_OK, getIntent());
            finish();
        });
    }

    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.RECIPES);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.recipe_display_nav_bar, navBar)
                .commit();
    }

    /**
     * Instantiates the top bar fragment for the recipe display menu
     */
    private void createTopBar() {
        TopBar topBar = TopBar.newInstance("Recipe Details", false, true);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.topBarContainerView, topBar)
                .commit();
    }
}