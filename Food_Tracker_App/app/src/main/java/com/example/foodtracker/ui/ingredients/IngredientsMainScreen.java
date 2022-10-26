package com.example.foodtracker.ui.ingredients;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

public class IngredientsMainScreen extends AppCompatActivity implements AddIngredientDialog.AddIngredientDialogListener {

    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());
    private final ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
    private final IngredientRecyclerViewAdapter adapter = new IngredientRecyclerViewAdapter(getBaseContext(), ingredientArrayList);

    public IngredientsMainScreen() {
        super(R.layout.ingredient_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeRecyclerView();
        initializeData();
        initializeAddIngredientButton();
        initializeBackButton();
        addNavbar(savedInstanceState);
    }

    private void initializeRecyclerView() {
        RecyclerView ingredientsRecyclerView = findViewById(R.id.ingredient_list);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        ingredientsRecyclerView.setAdapter(adapter);
    }

    private void addNavbar(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            NavBar navBar = NavBar.newInstance(MenuItem.INGREDIENTS);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, navBar)
                    .commit();
        }
    }

    @Override
    public void onIngredientAdd(Ingredient addedIngredient) {
        ingredientArrayList.add(addedIngredient);
        adapter.notifyItemInserted(ingredientArrayList.indexOf(addedIngredient));
    }

    @Override
    public void onCancel() {

    }

    /**
     * Initializes the add ingredient button to open the add ingredient dialog on click
     */
    private void initializeAddIngredientButton() {
        Button addIngredientButton = findViewById(R.id.add_ingredient_button);
        addIngredientButton.setOnClickListener(ingredientView -> new AddIngredientDialog().show(getSupportFragmentManager(), "Add_ingredient"));
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
        Ingredient tuna = new Ingredient("Tuna", 1.0, "Pantry", "Food", 5, "05/02/2022");
        Ingredient apple = new Ingredient("Apple", 1.0, "Pantry", "Food", 5, "05/02/2022");
        Ingredient broccoli = new Ingredient("Broccoli", 1.0, "Pantry", "Food", 5, "05/02/2022");
        ingredientArrayList.add(tuna);
        ingredientArrayList.add(apple);
        ingredientArrayList.add(broccoli);
        ingredientsCollection.createOrUpdateMultiple(ingredientArrayList);
        ingredientsCollection.getAll(list -> {
            ingredientArrayList.addAll(list);
            adapter.notifyItemRangeInserted(0, ingredientArrayList.size());
            System.out.println("NUM INGREDIENTS ADDED: " + ingredientArrayList.size());
        });
    }

    private void returnToMainMenu() {
        finish();
    }
}