package com.example.foodtracker.ui.recipes;

import static com.example.foodtracker.ui.recipes.AddRecipeActivity.RECIPE_KEY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class creates an object that is used to represent the main screen for the Recipes
 * This class extends {@link AppCompatActivity}
 */
public class RecipesMainScreen extends AppCompatActivity implements
        RecipeRecyclerViewAdapter.RecipeArrayListener,
        RecyclerViewInterface,
        TopBar.TopBarListener {

    private final Collection<Recipe> recipesCollection = new Collection<>(Recipe.class, new Recipe());
    private final ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    private final RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(this, recipeArrayList, this);
    private final ActivityResultLauncher<Intent> recipeActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
        if (activityResult.getData() != null && activityResult.getData().getExtras() != null) {
            Recipe receivedRecipe = (Recipe) activityResult.getData().getSerializableExtra(RECIPE_KEY);
            addRecipe(receivedRecipe);
        }
    });

    public RecipesMainScreen() {
        super(R.layout.recipes_main);
    }

    /**
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);
        initializeData();
        if (savedInstanceState == null) {
            createRecyclerView();
            createNavbar();
            createTopBar();
        }

        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            Recipe received_recipe = (Recipe) intent.getSerializableExtra("recipe_key");
            addRecipe(received_recipe);
        }

    }

    @Override
    public void onEdit(Recipe object) {

    }

    @Override
    public void onDelete(Recipe object) {

    }


    @Override
    public void onAddClick() {
        Intent intent = new Intent(getApplicationContext(), AddRecipeActivity.class);
        recipeActivityResultLauncher.launch(intent);
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
}