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
import com.example.foodtracker.ui.Sort;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.Objects;

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
    private final ActivityResultLauncher<Intent> recipeDisplayResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
        if (activityResult.getData() != null && activityResult.getData().getExtras() != null) {
            Recipe recipeToDelete = (Recipe) activityResult.getData().getSerializableExtra("DELETED_RECIPE");
            deleteRecipe(recipeToDelete);
        }
    });
    /**
     * Allows us to sort by a selected field name and refresh the data in the view
     */
    private Sort<Recipe.FieldName, RecipeRecyclerViewAdapter, Recipe> sort;
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
        initializeSort();
        if (savedInstanceState == null) {
            createRecyclerView();
            createNavbar();
            createTopBar();
        }

        Intent intent = getIntent();
        if (getIntent().getExtras() != null) {
            Recipe received_recipe = (Recipe) intent.getSerializableExtra("EDITED_RECIPE");
            editRecipe(received_recipe);
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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), RecipeDisplay.class);
        Recipe recipe = recipeArrayList.get(position);
        intent.putExtra("RECIPE", recipe);
        recipeDisplayResultLauncher.launch(intent);
    }

    public void editRecipe(Recipe recipe) {
        int editIndex = recipeArrayList.indexOf(recipe);
        recipesCollection.updateDocument(recipe, () -> adapter.notifyItemChanged(editIndex));
    }

    public void deleteRecipe(Recipe recipe) {
        int removedIndex = recipeArrayList.indexOf(recipe);
        recipeArrayList.remove(removedIndex);
        recipesCollection.delete(recipe, () ->
                adapter.notifyItemRemoved(removedIndex));

    }

    private void addRecipe(Recipe recipe) {
        recipeArrayList.add(recipe);
        recipesCollection.createDocument(recipe, () -> {
            adapter.notifyItemInserted(recipeArrayList.indexOf(recipe));
            sort.sortByFieldName();
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
        TopBar topBar = TopBar.newInstance("Recipes", true, false);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.topBarContainerView, topBar)
                .commit();
    }

    private void initializeSort() {
        sort = new Sort<>(this.recipesCollection, this.adapter, this.recipeArrayList, Recipe.FieldName.class);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.sort_spinnerRecipe, sort).commit();
    }
}