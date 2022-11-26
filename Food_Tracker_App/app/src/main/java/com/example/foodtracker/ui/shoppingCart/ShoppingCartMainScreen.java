package com.example.foodtracker.ui.shoppingCart;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtracker.MainActivity;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealplan.MealPlanDay;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.model.recipe.SimpleIngredient;
import com.example.foodtracker.model.recipe.SimpleIngredientNameComparator;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.Collection;
import com.example.foodtracker.utils.ConversionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This class is used to create an object that will be used to represent the Shopping Cart Main Screen
 * This class extends from {@link AppCompatActivity}
 */
public class ShoppingCartMainScreen extends AppCompatActivity implements
        ExpandableShoppingListAdapter.ShoppingListListener {

    private final Collection<Ingredient> ingredientCollection = new Collection<>(Ingredient.class, new Ingredient());
    private final Collection<MealPlanDay> mealPlanDayCollection = new Collection<>(MealPlanDay.class, new MealPlanDay());
    Set<SimpleIngredient> ingredientsInStorage = new HashSet<>();
    List<SimpleIngredient> ingredientsInShoppingList = new ArrayList<>();
    List<MealPlanDay> mealPlanDays = new ArrayList<>();
    ExpandableListView shoppingCartExpandableList;
    ExpandableShoppingListAdapter expandableListAdapter;
    Spinner spinner;
    String sortingFieldName;
    Map<String, Set<SimpleIngredient>> ingredientsByCategory;

    /**
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_main);
        spinner = findViewById(R.id.sort_spinner_shopping);
        initializeSpinner();
        shoppingCartExpandableList = findViewById(R.id.shopping_list_expandable);

        refreshAll();

        if (savedInstanceState == null) {
            createNavbar();
            createTopBar();
        }
    }

    @Override
    public void onCheck(SimpleIngredient ingredient) {
        checkoutIngredient(ingredient);
    }

    private void refreshAll() {
        mealPlanDays.clear();
        ingredientsInStorage.clear();
        mealPlanDayCollection.getAll(mealPlans -> {
            mealPlanDays.addAll(mealPlans);

            ingredientCollection.getAll(ingredients -> {
                List<SimpleIngredient> simpleIngredients = new ArrayList<>();
                for (Ingredient ingredient : ingredients) {
                    simpleIngredients.add(new SimpleIngredient(ingredient));
                }
                ingredientsInStorage.addAll(combineLikewiseIngredients(simpleIngredients, Boolean.FALSE));
                refreshShoppingList();
            });
        });
    }

    private void refreshShoppingList() {
        ingredientsInShoppingList.clear();
        ingredientsByCategory = new HashMap<>();
        setIngredientsInShoppingList(getRequiredIngredients());
        setIngredientsByCategory(Boolean.FALSE);
        expandableListAdapter = new ExpandableShoppingListAdapter(this, ingredientsByCategory.keySet(), ingredientsByCategory);
        shoppingCartExpandableList.setAdapter(expandableListAdapter);
    }

    private Set<SimpleIngredient> getRequiredIngredients() {
        List<SimpleIngredient> requiredIngredients = new ArrayList<>();
        for (MealPlanDay mealPlanDay : mealPlanDays) {
            for (Ingredient ingredient : mealPlanDay.getIngredients()) {
                requiredIngredients.add(new SimpleIngredient(ingredient));
            }
            for (Recipe recipe : mealPlanDay.getRecipes()) {
                requiredIngredients.addAll(recipe.getIngredients());
            }
        }
        return combineLikewiseIngredients(requiredIngredients, Boolean.FALSE);
    }

    private Set<SimpleIngredient> combineLikewiseIngredients(List<SimpleIngredient> ingredients, Boolean sorted) {
        Set<SimpleIngredient> mergedIngredients;
        if (sorted){
            mergedIngredients = new TreeSet<>(new SimpleIngredientNameComparator());
        }else{
            mergedIngredients = new HashSet<>();
        }

        for (int i = 0; i < ingredients.size(); i++) {
            boolean ingredientAdded = false;
            SimpleIngredient ingredientA = ingredients.get(i);
            for (int j = i + 1; j < ingredients.size(); j++) {
                SimpleIngredient ingredientB = ingredients.get(j);
                if (i != j && ingredientA.equals(ingredientB)) {
                    try {
                        ingredientA.addIngredientAmount(ingredientB.getIngredientAmount());
                        mergedIngredients.add(ingredientA);
                        ingredientAdded = true;
                    } catch (IllegalArgumentException illegalArgumentException) {
                        // do nothing
                    }
                }
            }
            if (!ingredientAdded) {
                mergedIngredients.add(ingredientA);
            }
        }
        return mergedIngredients;
    }

    private void setIngredientsByCategory(Boolean sorted) {
        for (SimpleIngredient shoppingListIngredient : ingredientsInShoppingList) {
            String shoppingCategory = shoppingListIngredient.getCategory().getName();
            Set<SimpleIngredient> ingredientsInCategory;
            if (ingredientsByCategory.containsKey(shoppingCategory)) {
                ingredientsInCategory = Objects.requireNonNull(ingredientsByCategory.get(shoppingCategory));
                List<SimpleIngredient> categoryWithAddedIngredient = new ArrayList<>(ingredientsInCategory);
                categoryWithAddedIngredient.add(shoppingListIngredient);
                if (!sorted){
                    ingredientsInCategory = combineLikewiseIngredients(categoryWithAddedIngredient, Boolean.FALSE);
                }else{
                    ingredientsInCategory = combineLikewiseIngredients(categoryWithAddedIngredient, Boolean.TRUE);
                }
            } else {
                if (!sorted){
                    ingredientsInCategory = new HashSet<>();
                }else{
                    ingredientsInCategory = new TreeSet<>(new SimpleIngredientNameComparator());
                }

                ingredientsInCategory.add(shoppingListIngredient);
            }
            ingredientsByCategory.put(shoppingCategory, ingredientsInCategory);
        }
    }

    private void setIngredientsInShoppingList(Set<SimpleIngredient> ingredientsRequired) {
        for (SimpleIngredient requiredIngredient : ingredientsRequired) {
            boolean consideredForShoppingList = false;
            for (SimpleIngredient ingredientInStorage : ingredientsInStorage) {
                if (ingredientInStorage.equals(requiredIngredient)) {
                    SimpleIngredient ingredientToPurchase = new SimpleIngredient();
                    try {
                        ingredientToPurchase.setIngredientAmount(ConversionUtil.getMissingAmount(ingredientInStorage.getIngredientAmount(), requiredIngredient.getIngredientAmount()));
                    } catch (IllegalArgumentException illegalArgumentException) {
                        ingredientToPurchase.setIngredientAmount(requiredIngredient.getIngredientAmount());
                    }
                    ingredientToPurchase.setCategoryName(requiredIngredient.getCategory().getName());
                    ingredientToPurchase.setDescription(requiredIngredient.getDescription());
                    if (ingredientToPurchase.getAmountQuantity() > 0) {
                        ingredientsInShoppingList.add(ingredientToPurchase);

                    }
                    consideredForShoppingList = true;
                }
            }
            if (!consideredForShoppingList) {
                ingredientsInShoppingList.add(requiredIngredient);
            }
        }
    }

    private void checkoutIngredient(SimpleIngredient ingredient) {
        Ingredient newIngredient = new Ingredient(ingredient);
        ingredientCollection.createDocument(newIngredient, this::refreshAll);
    }

    private void initializeSpinner(){
        spinner = (Spinner) findViewById(R.id.sort_spinner_shopping);
        ArrayList<String> fields = new ArrayList<>();
        fields.add("");
        fields.add("Category");
        fields.add("Description");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, fields);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        initializeSelectedItemListener(spinner);
    }

    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.SHOPPING_CART);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.shopping_cart_nav_bar, navBar).commit();
    }

    /**
     * Instantiates the top bar fragment for the ingredients menu
     */
    private void createTopBar() {
        TopBar topBar = TopBar.newInstance("Shopping List", false, false);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.topBarContainerView, topBar)
                .commit();
    }

    private void initializeSelectedItemListener(Spinner sortSpinner) {
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView toSort = view.findViewById(android.R.id.text1);
                sortingFieldName = toSort.getText().toString();
                sortByFieldName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortByFieldName();
            }
        });
    }

    public void sortByFieldName() {
        if (sortingFieldName.equals("Category")){
            ingredientsInShoppingList.clear();
            ingredientsByCategory = new TreeMap<>();
            setIngredientsInShoppingList(getRequiredIngredients());
            setIngredientsByCategory(Boolean.FALSE);
            expandableListAdapter = new ExpandableShoppingListAdapter(this, ingredientsByCategory.keySet(), ingredientsByCategory);
            shoppingCartExpandableList.setAdapter(expandableListAdapter);
        }

        if (sortingFieldName.equals("")){
            ingredientsInShoppingList.clear();
            ingredientsByCategory = new HashMap<>();
            setIngredientsInShoppingList(getRequiredIngredients());
            setIngredientsByCategory(Boolean.FALSE);
            expandableListAdapter = new ExpandableShoppingListAdapter(this, ingredientsByCategory.keySet(), ingredientsByCategory);
            shoppingCartExpandableList.setAdapter(expandableListAdapter);
        }

        if (sortingFieldName.equals("Description")){
            ingredientsInShoppingList.clear();
            ingredientsByCategory = new HashMap<>();
            setIngredientsInShoppingList(getRequiredIngredients());
            setIngredientsByCategory(Boolean.TRUE);
            expandableListAdapter = new ExpandableShoppingListAdapter(this, ingredientsByCategory.keySet(), ingredientsByCategory);
            shoppingCartExpandableList.setAdapter(expandableListAdapter);
        }
    }
}