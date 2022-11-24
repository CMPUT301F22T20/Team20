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
        TopBar.TopBarListener {

    private final Collection<Ingredient> shoppingItemCollection = new Collection<>(Ingredient.class, new Ingredient());
    private final Collection<MealPlanDay> mealPlanDayCollection = new Collection<>(MealPlanDay.class, new MealPlanDay());
    List<Ingredient> ingredientsInStorage = new ArrayList<>();
    List<SimpleIngredient> ingredientsInShoppingList = new ArrayList<>();
    List<MealPlanDay> mealPlanDays = new ArrayList<>();
    ExpandableListView shoppingCartExpandableList;
    ExpandableShoppingListAdapter expandableListAdapter;
    Map<String, Set<SimpleIngredient>> ingredientsByCategory = new HashMap<>();

    /**
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_main);

        shoppingCartExpandableList = findViewById(R.id.shopping_list_expandable);

        mealPlanDayCollection.getAll(mealPlans -> {
            mealPlanDays.addAll(mealPlans);
            shoppingItemCollection.getAll(ingredients -> {
                ingredientsInStorage.addAll(ingredients);
                initializeShoppingList();
            });
        });

        if (savedInstanceState == null) {
            createNavbar();
            createTopBar();
        }
    }

    @Override
    public void onAddClick() {

    }

    private void initializeShoppingList() {
        setIngredientsInShoppingList(getRequiredIngredients());
        initializeShoppingListView();
    }

    // Should be good
    private List<SimpleIngredient> getRequiredIngredients() {
        List<SimpleIngredient> requiredIngredients = new ArrayList<>();
        for (MealPlanDay mealPlanDay : mealPlanDays) {
            requiredIngredients.addAll(mealPlanDay.getIngredients());
            for (Recipe recipe : mealPlanDay.getRecipes()) {
                requiredIngredients.addAll(recipe.getIngredients());
            }
        }
        return requiredIngredients;
    }

    // Not sure if completely working
    private void initializeShoppingListView() {
        for (SimpleIngredient requiredIngredient : ingredientsInShoppingList) {
            String shoppingCategory = requiredIngredient.getCategory().getName();
            if (ingredientsByCategory.containsKey(shoppingCategory)) {
                Set<SimpleIngredient> ingredientsInCategory = Objects.requireNonNull(ingredientsByCategory.get(shoppingCategory));
                SimpleIngredient ingredientInShoppingList = getIngredient(ingredientsInCategory, requiredIngredient);
                if (ingredientInShoppingList != null) {
                    ingredientInShoppingList.addIngredientAmount(requiredIngredient.getAmount());
                } else {
                    ingredientsInCategory.add(requiredIngredient);
                }
            } else {
                Set<SimpleIngredient> ingredientsToPurchase = new HashSet<>();
                ingredientsToPurchase.add(requiredIngredient);
                ingredientsByCategory.put(shoppingCategory, ingredientsToPurchase);
            }
        }
        expandableListAdapter = new ExpandableShoppingListAdapter(this, ingredientsByCategory.keySet(), ingredientsByCategory);
        shoppingCartExpandableList.setAdapter(expandableListAdapter);
    }

    private SimpleIngredient getIngredient(Set<SimpleIngredient> ingredients, SimpleIngredient ingredient) {
        for (SimpleIngredient ingredientInShoppingList : ingredients) {
            if (ingredient.equals(ingredientInShoppingList)) {
                return ingredientInShoppingList;
            }
        }
        return null;
    }

    // Not quite working yet
    private void setIngredientsInShoppingList(List<SimpleIngredient> ingredientsRequired) {
        for (SimpleIngredient requiredIngredient : ingredientsRequired) {
            boolean consideredForShoppingList = false;
            for (Ingredient ingredientInStorage : ingredientsInStorage) {
                if (ingredientInStorage.getDescription().equals(requiredIngredient.getDescription())) {
                    SimpleIngredient ingredientToPurchase = new SimpleIngredient();
                    ingredientToPurchase.setIngredientAmount(ConversionUtil.getMissingAmount(ingredientInStorage.getAmount(), requiredIngredient.getAmount()));
                    ingredientToPurchase.setCategory(requiredIngredient.getCategory().getName());
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


    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.SHOPPING_CART);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.shopping_cart_nav_bar, navBar).commit();
    }

    /**
     * Instantiates the top bar fragment for the ingredients menu
     */
    private void createTopBar() {
        TopBar topBar = TopBar.newInstance("Shopping List", true, false);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.topBarContainerView, topBar)
                .commit();
    }
}