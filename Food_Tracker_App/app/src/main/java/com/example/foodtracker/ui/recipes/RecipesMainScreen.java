package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.Collection;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
        initializeSort();
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
        startActivity(intent);
    }

    /**
     *
     * @see <a href= "https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event">Stack Overflow</a>
     * Copyright: CC BY-SA 3.0 (answer edited by Vasily Kabunov)
     *
     * @see <a href = "https://developer.android.com/develop/ui/views/components/spinner#java"> Android Developers</a>
     * Copyright: Apache 2.0
     *
     */
    private void initializeSort(){

        Spinner sortSpinnerRecipe= findViewById(R.id.sort_spinnerRecipe);
        ArrayAdapter<CharSequence> sortAdapterRecipe = ArrayAdapter.createFromResource(this,
                R.array.sortRecipes, android.R.layout.simple_spinner_item);
        sortAdapterRecipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinnerRecipe.setAdapter(sortAdapterRecipe);

        sortSpinnerRecipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Collections.sort(recipeArrayList, new Comparator<Recipe>(){
                    @Override
                    public int compare(Recipe o1, Recipe o2) {

                        switch(position) {
                            case 1:
                                return o1.getTitle().compareToIgnoreCase(o2.getTitle());
                            case 2:
                                return o2.getTitle().compareToIgnoreCase(o1.getTitle());
                            case 3:
                                return Integer.compare(o1.getPrepTime(),o2.getPrepTime());
                            case 4:
                                return Integer.compare(o2.getPrepTime(),o1.getPrepTime());
                            case 5:
                                return Integer.compare(o1.getServings(),o2.getServings());
                            case 6:
                                return Integer.compare(o2.getServings(),o1.getServings());
                            case 7:
                                return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                            case 8:
                                return o2.getCategory().compareToIgnoreCase(o1.getCategory());
                        }
                        return 0;
                    }
                });
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
}