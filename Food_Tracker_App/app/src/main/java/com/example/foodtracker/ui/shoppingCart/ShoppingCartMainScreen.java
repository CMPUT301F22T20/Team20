package com.example.foodtracker.ui.shoppingCart;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.Sort;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.ui.recipes.RecipeRecyclerViewAdapter;
import com.example.foodtracker.ui.recipes.RecyclerViewInterface;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class is used to create an object that will be used to represent the Shopping Cart Main Screen
 * This class extends from {@link AppCompatActivity}
 */
public class ShoppingCartMainScreen extends AppCompatActivity implements
            ShoppingCategoryRecyclerViewAdapter.ShoppingCategoryArrayListener,
            ShoppingItemRecyclerViewAdapter.ShoppingItemArrayListener,
            RecyclerViewInterface,
            TopBar.TopBarListener {

        private final Collection<Ingredient> shoppingItemCollection = new Collection<>(Ingredient.class, new Ingredient());
        private final ArrayList<Ingredient> shoppingItemArrayList = new ArrayList<>();
        private final ArrayList<String> shoppingCategoryArrayList = new ArrayList<>();
        private final ShoppingCategoryRecyclerViewAdapter categoryAdapter = new ShoppingCategoryRecyclerViewAdapter(this, shoppingCategoryArrayList, this);
        private final ShoppingItemRecyclerViewAdapter itemAdapter = new ShoppingItemRecyclerViewAdapter(this, shoppingItemArrayList, this);
//        private final ActivityResultLauncher<Intent> recipeDisplayResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), activityResult -> {
//            if (activityResult.getData() != null && activityResult.getData().getExtras() != null) {
//                Recipe recipeToDelete = (Recipe) activityResult.getData().getSerializableExtra("DELETED_RECIPE");
//                deleteRecipe(recipeToDelete);
//            }
//        });
    /**
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_main);

        if (savedInstanceState == null) {
            NavBar navBar = NavBar.newInstance(MenuItem.SHOPPING_CART);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.shopping_cart_nav_bar, navBar)
                    .commit();
        }
    }

    private void createCategoryRecyclerViewContent() {
        RecyclerView recipeRecyclerView = findViewById(R.id.shoppinglist_list);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setAdapter(categoryAdapter);
    }

    private void createItemRecyclerViewContent() {
        RecyclerView recipeRecyclerView = findViewById(R.id.shoppinglist_item_list);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setAdapter(itemAdapter);
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

    @Override
    public void onEdit(Ingredient object) {

    }

    @Override
    public void onDelete(Ingredient object) {

    }

    @Override
    public void onAddClick() {

    }

    @Override
    public void onItemClick(int position) {

    }
}