package com.example.foodtracker.ui.ingredients;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class creates an object that is used to represent the main screen for the Ingredients
 * This class extends from {@link AppCompatActivity}
 * THis class implements the {@link IngredientDialog.IngredientDialogListener} from {@link IngredientDialog} class
 */
public class IngredientsMainScreen extends AppCompatActivity implements
        IngredientDialog.IngredientDialogListener,
        IngredientRecyclerViewAdapter.IngredientArrayListener {

    /**
     * This is a private final variable
     * This holds a collection of {@link Ingredient} objects and is of type {@link Ingredient}
     */
    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());
    /**
     * This is a private variable
     * This holds the adapter for the ingredient list
     */
    private final ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
    /**
     * This is a private variable
     * This holds a list of {@link Ingredient} objects and is of type {@link ArrayList<Ingredient>}
     */
    private final IngredientRecyclerViewAdapter adapter = new IngredientRecyclerViewAdapter(this, ingredientArrayList);

    /**
     * This is the constructor for the class
     */
    public IngredientsMainScreen() {
        super(R.layout.ingredient_main);
    }

    /**
     * This function is called when a main screen object is created
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
        initializeAddIngredientButton();
        initializeBackButton();
        if (savedInstanceState == null) {
            createRecyclerView();
            createNavbar();
        }
    }

    /**
     * This is called when an Ingredient is added by clicking on the add button
     * This is the implementation of a function from {@link IngredientDialog.IngredientDialogListener}
     * @param addedIngredient This is the Ingredient that is added which is of type {@link Ingredient}
     */
    @Override
    public void onIngredientAdd(Ingredient addedIngredient) {
        addIngredient(addedIngredient);
    }

    /**
     * TODO: Here we would like to open the ingredient dialog with an ingredient
     */
    @Override
    public void onEdit(Ingredient ingredient) {
    }

    @Override
    public void onDelete(Ingredient ingredient) {
        removeIngredient(ingredient);
    }

    private void createRecyclerView() {
        RecyclerView ingredientsRecyclerView = findViewById(R.id.ingredient_list);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsRecyclerView.setAdapter(adapter);
    }

    private void addIngredient(Ingredient ingredient) {
        ingredientArrayList.add(ingredient);
        ingredientsCollection.createOrUpdate(ingredient);
        adapter.notifyItemInserted(ingredientArrayList.indexOf(ingredient));
    }

    private void removeIngredient(Ingredient ingredient) {
        int removedIndex = ingredientArrayList.indexOf(ingredient);
        ingredientArrayList.remove(removedIndex);
        ingredientsCollection.delete(ingredient);
        adapter.notifyItemRemoved(removedIndex);
    }

    /**
     * Instantiates the navbar fragment for the ingredients menu
     */
    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.INGREDIENTS);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, navBar)
                .commit();
    }

    /**
     * Initializes the add ingredient button to open the add ingredient dialog on click
     */
    private void initializeAddIngredientButton() {
        Button addIngredientButton = findViewById(R.id.add_ingredient_button);
        addIngredientButton.setOnClickListener(ingredientView -> new IngredientDialog().show(getSupportFragmentManager(), "Add_ingredient"));
    }

    /**
     * Initializes the add ingredient button to open the add ingredient dialog on click
     */
    private void initializeBackButton() {
        Button backButton = findViewById(R.id.return_button);
        backButton.setOnClickListener(ingredientView -> returnToMainMenu());
    }

    /**
     * Adds some initial data to the list
     */
    private void initializeData() {
        ingredientsCollection.getAll(list -> {
            ingredientArrayList.addAll(list);
            adapter.notifyItemRangeInserted(0, ingredientArrayList.size());
        });
    }

    /**
     * This function helps to return back to main menu
     */
    private void returnToMainMenu() {
        finish();
    }
}