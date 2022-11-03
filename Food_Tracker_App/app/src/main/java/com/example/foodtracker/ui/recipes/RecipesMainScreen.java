package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.ui.ingredients.IngredientDialog;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class creates an object that is used to represent the main screen for the Recipes
 * This class extends {@link AppCompatActivity}
 */
public class RecipesMainScreen extends AppCompatActivity implements
        RecipeDialog.RecipeDialogListener,
        RecipeRecyclerViewAdapter.RecipeArrayListener,
        RecyclerViewInterface,
        TopBar.TopBarListener {

    private final Collection<Recipe> recipesCollection = new Collection<>(Recipe.class, new Recipe());
    private final ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    private final RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(this, recipeArrayList, this);

    public RecipesMainScreen() {
        super(R.layout.recipes_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);
        initializeData();
        // addRecipe(new Recipe("image", "Chocolate Chip Cookies", 60, 24, "Dessert", "", new ArrayList<Ingredient>()));
        // addRecipe(new Recipe("image", "Sugar Cookies", 55, 24, "Dessert", "", new ArrayList<Ingredient>()));
        if (savedInstanceState == null) {
            createRecyclerView();
            createNavbar();
            createTopBar();
        }
    }

    @Override
    public void onRecipeAdd(Recipe addedRecipe) {
        addRecipe(addedRecipe);
    }

    @Override
    public void onEdit(Recipe object) {

    }

    @Override
    public void onDelete(Recipe object) {

    }


    @Override
    public void onAddClick() {
        new RecipeDialog().show(getSupportFragmentManager(), "Add_Recipe");
    }

    private void addRecipe(Recipe recipe) {
        recipeArrayList.add(recipe);
        recipesCollection.createDocument(recipe, () ->
                adapter.notifyItemInserted(recipeArrayList.indexOf(recipe)));
    }

    /**
     * Adds some initial data to the list
     */
    private void initializeData() {
        recipesCollection.getAll(list -> {
            recipeArrayList.addAll(list);
            adapter.notifyItemRangeInserted(0, recipeArrayList.size());
        });
    }

    /**
     * This function closes the activity and returns back to main menu
     */
    private void returnToMainMenu() {
        finish();
    }

    private void createRecyclerView() {
        RecyclerView recipeRecyclerView = findViewById(R.id.recipe_list);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setAdapter(adapter);
    }

    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.RECIPES);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.recipes_nav_bar, navBar)
                .commit();
    }

    /**
     * Instantiates the top bar fragment for the ingredients menu
     */
    private void createTopBar() {
        TopBar topBar = TopBar.newInstance("Recipes", true);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.topBarContainerView, topBar)
                .commit();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RecipeDisplay.class);
        Recipe recipe = recipeArrayList.get(position);
        intent.putExtra("RECIPE", recipe);
        startActivity(intent);
    }
}