package com.example.foodtracker.ui.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.BitmapUtil;

public class RecipeDisplay extends AppCompatActivity {

    private TextView recipeTitle;
    private TextView recipePrepTime;
    private TextView recipeServings;
    private TextView recipeCategory;
    private TextView recipeComment;
    //private ListView recipeIngredients;
    private RecyclerView recipeIngredients;
    private Button deleteRecipeButton;
    private Button editRecipeButton;
    private ImageView recipeImage;
    private Uri imageURI;

    //private ArrayAdapter<Ingredient> adapter;
    private RecipeIngredientsRecyclerViewAdapter adapter;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_display);

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("RECIPE");

        //adapter = new CustomList(this, recipe.getIngredients());
        adapter = new RecipeIngredientsRecyclerViewAdapter(this, recipe.getIngredients());

        recipeTitle = findViewById(R.id.recipeDisplayTitle);
        recipePrepTime = findViewById(R.id.recipeDisplayPrepTime);
        recipeServings = findViewById(R.id.recipeDisplayServings);
        recipeCategory = findViewById(R.id.recipeDisplayCategory);
        recipeComment = findViewById(R.id.recipeDisplayComment);
        recipeIngredients = findViewById(R.id.recipeDisplayIngredientList);
        deleteRecipeButton = findViewById(R.id.recipeDeleteButton);
        editRecipeButton = findViewById(R.id.recipeEditButton);
        recipeImage = findViewById(R.id.recipe_display_image);

        recipeTitle.setText(recipe.getTitle());
        recipePrepTime.setText("Preperation Time: " + String.valueOf(recipe.getPrepTime()));
        recipeServings.setText("Servings: " + String.valueOf(recipe.getServings()));
        recipeCategory.setText("Category: " + recipe.getCategory());
        recipeComment.setText("Comment:\n\n" + recipe.getComment());
        if (!recipe.getImage().isEmpty()) {
            recipeImage.setImageBitmap(BitmapUtil.fromString(recipe.getImage()));
        }

        recipeIngredients.setLayoutManager(new LinearLayoutManager(this));
        recipeIngredients.setAdapter(adapter);

        if (savedInstanceState == null) {
            createNavbar();
            createTopBar();
        }

        editRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), EditRecipeActivity.class);
//                intent.putExtra("EDIT_RECIPE", recipe);
//                startActivity(intent);
            }
        });

        deleteRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("DELETED_RECIPE", recipe);
                setResult(RESULT_OK, intent);
                finish();
            }
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