package com.example.foodtracker.ui.ingredients;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.MenuItem;
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
 * This class creates an object that is used to represent the main screen for the Ingredients
 * This class extends from {@link AppCompatActivity}
 * THis class implements the {@link IngredientDialog.IngredientDialogListener} from {@link IngredientDialog} class
 * Implements {@link com.example.foodtracker.ui.TopBar.TopBarListener} to provide callback on add press
 */
public class IngredientsMainScreen extends AppCompatActivity implements
        IngredientDialog.IngredientDialogListener,
        IngredientRecyclerViewAdapter.IngredientArrayListener,
        TopBar.TopBarListener {

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
        initializeSort();
        if (savedInstanceState == null) {
            createRecyclerView();
            createNavbar();
            createTopBar();
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

    @Override
    public void onIngredientEdit(Ingredient oldIngredient) {
        editIngredient(oldIngredient);
    }

    /**
     * TODO: Here we would like to open the ingredient dialog with an ingredient
     */
    @Override
    public void onEdit(Ingredient ingredient) {
        Bundle args = new Bundle();
        args.putSerializable("ingredient", ingredient);

        IngredientDialog edit_fragment = new IngredientDialog();
        edit_fragment.setArguments(args);
        edit_fragment.show(getSupportFragmentManager(), "EDIT_INGREDIENT");
    }

    @Override
    public void onDelete(Ingredient ingredient) {
        removeIngredient(ingredient);
    }

    @Override
    public void onAddClick() {
        new IngredientDialog().show(getSupportFragmentManager(), "Add_ingredient");
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

    private void editIngredient(Ingredient ingredient) {
        int editIndex = ingredientArrayList.indexOf(ingredient);
        ingredientsCollection.editDocument(ingredient, () -> adapter.notifyItemChanged(editIndex));
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
                .replace(R.id.navbarContainerView, navBar)
                .commit();
    }

    /**
     * Instantiates the top bar fragment for the ingredients menu
     */
    private void createTopBar() {
        TopBar topBar = TopBar.newInstance("Ingredients", true);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.topBarContainerView, topBar)
                .commit();
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
     * Code and design is reused from category and location spinners in IngredientDialog
     * @param spinnerDataXML
     * @return adapter
     */

    private ArrayAdapter<CharSequence> createAdapter(int spinnerDataXML) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, spinnerDataXML, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event
     * This sorts the ingredients in IngredientArrayList based on the user's chosen field
     * Case numbers refer to the position of the chosen field in the array inside spinner
     * ex. "Sort by: " = index 0, "Description (ASC)" = index 1,...
     */

    private void initializeSort(){

        Spinner sortSpinner= findViewById(R.id.sort_spinner);
        ArrayAdapter<CharSequence> sortAdapter = createAdapter(R.array.sortIngredients);
        sortSpinner.setAdapter(sortAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Collections.sort(ingredientArrayList, new Comparator<Ingredient>(){
                    @Override
                    public int compare(Ingredient o1, Ingredient o2) {

                        switch(position) {
                            case 1:
                                return o1.getDescription().compareToIgnoreCase(o2.getDescription());
                            case 2:
                                return o2.getDescription().compareToIgnoreCase(o1.getDescription());
                            case 3:
                                return o1.getCategory().compareToIgnoreCase(o2.getCategory());
                            case 4:
                                return o2.getCategory().compareToIgnoreCase(o1.getCategory());
                            case 5:
                                DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                                try {
                                    Date date = format.parse(o1.getExpiry());
                                    Date date2 = format.parse(o2.getExpiry());
                                    return date.compareTo(date2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            case 6:
                                format = new SimpleDateFormat("MM-dd-yyyy");
                                try {
                                    Date date = format.parse(o1.getExpiry());
                                    Date date2 = format.parse(o2.getExpiry());
                                    return date2.compareTo(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            case 7:
                                return o1.getLocation().compareToIgnoreCase(o2.getLocation());
                            case 8:
                                return o2.getLocation().compareToIgnoreCase(o1.getLocation());
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