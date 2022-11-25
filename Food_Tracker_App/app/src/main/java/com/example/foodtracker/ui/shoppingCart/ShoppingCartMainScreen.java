package com.example.foodtracker.ui.shoppingCart;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealplan.MealPlanDay;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.model.recipe.SimpleIngredient;
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
    Map<String, Set<SimpleIngredient>> ingredientsByCategory;

    /**
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_main);

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
                ingredientsInStorage.addAll(combineLikewiseIngredients(simpleIngredients));
                refreshShoppingList();
            });
        });
    }

    private void refreshShoppingList() {
        ingredientsInShoppingList.clear();
        ingredientsByCategory = new HashMap<>();
        setIngredientsInShoppingList(getRequiredIngredients());
        setIngredientsByCategory();
        expandableListAdapter = new ExpandableShoppingListAdapter(this, ingredientsByCategory.keySet(), ingredientsByCategory);
        shoppingCartExpandableList.setAdapter(expandableListAdapter);
    }

    private Set<SimpleIngredient> getRequiredIngredients() {
        List<SimpleIngredient> requiredIngredients = new ArrayList<>();
        for (MealPlanDay mealPlanDay : mealPlanDays) {
            requiredIngredients.addAll(mealPlanDay.getIngredients());
            for (Recipe recipe : mealPlanDay.getRecipes()) {
                requiredIngredients.addAll(recipe.getIngredients());
            }
        }
        return combineLikewiseIngredients(requiredIngredients);
    }

    private Set<SimpleIngredient> combineLikewiseIngredients(List<SimpleIngredient> ingredients) {
        Set<SimpleIngredient> mergedIngredients = new HashSet<>();
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

    private void setIngredientsByCategory() {
        for (SimpleIngredient shoppingListIngredient : ingredientsInShoppingList) {
            String shoppingCategory = shoppingListIngredient.getCategory().getName();
            Set<SimpleIngredient> ingredientsInCategory;
            if (ingredientsByCategory.containsKey(shoppingCategory)) {
                ingredientsInCategory = Objects.requireNonNull(ingredientsByCategory.get(shoppingCategory));
                List<SimpleIngredient> categoryWithAddedIngredient = new ArrayList<>(ingredientsInCategory);
                categoryWithAddedIngredient.add(shoppingListIngredient);
                ingredientsInCategory = combineLikewiseIngredients(categoryWithAddedIngredient);
            } else {
                ingredientsInCategory = new HashSet<>();
                ingredientsInCategory.add(shoppingListIngredient);
            }
            ingredientsByCategory.put(shoppingCategory, ingredientsInCategory);
        }
    }

    private void setIngredientsInShoppingList(Set<SimpleIngredient> ingredientsRequired) {
        for (SimpleIngredient requiredIngredient : ingredientsRequired) {
            boolean consideredForShoppingList = false;
            for (SimpleIngredient ingredientInStorage : ingredientsInStorage) {
                if (ingredientInStorage.getDescription().equals(requiredIngredient.getDescription())) {
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
}