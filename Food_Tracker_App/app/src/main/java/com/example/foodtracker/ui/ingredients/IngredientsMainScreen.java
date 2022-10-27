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

public class IngredientsMainScreen extends AppCompatActivity implements
        IngredientDialog.IngredientDialogListener,
        IngredientRecyclerViewAdapter.IngredientArrayListener {

    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());
    private final ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
    private final IngredientRecyclerViewAdapter adapter = new IngredientRecyclerViewAdapter(this, ingredientArrayList);

    public IngredientsMainScreen() {
        super(R.layout.ingredient_main);
    }

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
        ingredientsCollection.createDocument(ingredient, () ->
                adapter.notifyItemInserted(ingredientArrayList.indexOf(ingredient)));
    }

    private void removeIngredient(Ingredient ingredient) {
        int removedIndex = ingredientArrayList.indexOf(ingredient);
        ingredientArrayList.remove(removedIndex);
        ingredientsCollection.delete(ingredient, () ->
                adapter.notifyItemRemoved(removedIndex));
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

    private void returnToMainMenu() {
        finish();
    }
}