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

/**
 * This class creates an object that is used to represent the main screen for the Ingredients
 * This class extends from {@link AppCompatActivity}
 * THis class implements the {@link AddIngredientDialog.AddIngredientDialogListener} from {@link AddIngredientDialog} class
 */
public class IngredientsMainScreen extends AppCompatActivity implements AddIngredientDialog.AddIngredientDialogListener {

    /**
     * This is a private final variable
     * This holds a collection of {@link Ingredient} objects and is of type {@link Ingredient}
     */
    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());
    /**
     * This is a private variable
     * This holds the adapter for the ingredient list
     */
    private IngredientRecyclerViewAdapter adapter;
    /**
     * This is a private variable
     * This holds a list of {@link Ingredient} objects and is of type {@link ArrayList<Ingredient>}
     */
    private ArrayList<Ingredient> ingredientArrayList;

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
        setContentView(R.layout.ingredient_main);
        initializeData();

        RecyclerView ingredientsRecyclerView = findViewById(R.id.ingredient_list);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapter = new IngredientRecyclerViewAdapter(getBaseContext(), ingredientArrayList);
        ingredientsRecyclerView.setAdapter(adapter);
        adapter.notifyItemRangeInserted(0, ingredientArrayList.size());
        initializeAddIngredientButton();
        initializeBackButton();
        if (savedInstanceState == null) {
            NavBar navBar = NavBar.newInstance(MenuItem.INGREDIENTS);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainerView, navBar)
                    .commit();
        }
    }

    /**
     * This is called when an Ingredient is added by clicking on the add button
     * This is the implementation of a function from {@link AddIngredientDialog.AddIngredientDialogListener}
     * @param addedIngredient This is the Ingredient that is added which is of type {@link Ingredient}
     */
    @Override
    public void onIngredientAdd(Ingredient addedIngredient) {
        ingredientArrayList.add(addedIngredient);
        adapter.notifyItemInserted(ingredientArrayList.indexOf(addedIngredient));
    }

    /**
     * This is called when cancel button is clicked
     * This is the implementation of a function from {@link AddIngredientDialog.AddIngredientDialogListener}
     */
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
        backButton.setOnClickListener(ingredientView -> {
            returnToMainMenu();
        });
    }

    /**
     * Adds some initial data to the list
     */
    private void initializeData() {
        ingredientArrayList = new ArrayList<>();
        Ingredient tuna = new Ingredient("Tuna");
        Ingredient apple = new Ingredient("Apple");
        Ingredient broccoli = new Ingredient("Broccoli");
        ingredientArrayList.add(tuna);
        ingredientArrayList.add(apple);
        ingredientArrayList.add(broccoli);
        // todo: figure out why this is not working...
        ingredientsCollection.createOrUpdateMultiple(ingredientArrayList);
    }

    /**
     * This function helps to return back to main menu
     */
    private void returnToMainMenu() {
        finish();
    }
}