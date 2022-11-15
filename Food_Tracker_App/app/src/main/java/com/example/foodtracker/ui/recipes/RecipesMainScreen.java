package com.example.foodtracker.ui.recipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.Sort;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class creates an object that is used to represent the main screen for the Recipes
 * This class extends {@link AppCompatActivity}
 */
public class RecipesMainScreen extends AppCompatActivity implements RecipeDialog.RecipeDialogListener, RecipeRecyclerViewAdapter.RecipeArrayListener, RecyclerViewInterface, TopBar.TopBarListener {

    private final Collection<Recipe> recipesCollection = new Collection<>(Recipe.class, new Recipe());
    private final ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    private final RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(this, recipeArrayList, this);

    /**
     * Allows us to sort by a selected field name and refresh the data in the view
     */
    private Sort<Recipe.FieldName, RecipeRecyclerViewAdapter, Recipe> sort;

    public RecipesMainScreen() {
        super(R.layout.recipes_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);
        initializeData();
        initializeSort();
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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, RecipeDisplay.class);
        startActivity(intent);
    }

    private void addRecipe(Recipe recipe) {
        recipeArrayList.add(recipe);
        recipesCollection.createDocument(recipe, () -> {
            adapter.notifyItemInserted(recipeArrayList.indexOf(recipe));
            sort.sortByFieldName();
        });
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

    private void createRecyclerView() {
        RecyclerView recipeRecyclerView = findViewById(R.id.recipe_list);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setAdapter(adapter);
    }

    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.RECIPES);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.recipes_nav_bar, navBar).commit();
    }

    /**
     * Instantiates the top bar fragment for the ingredients menu
     */
    private void createTopBar() {
        TopBar topBar = TopBar.newInstance("Recipes", true);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.topBarContainerView, topBar).commit();
    }

    private void initializeSort() {
        Sort<Recipe.FieldName, RecipeRecyclerViewAdapter, Recipe> sort = new Sort<>(this.recipesCollection, this.adapter, this.recipeArrayList, Recipe.FieldName.class);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.sort_spinnerRecipe, sort).commit();
    }
}