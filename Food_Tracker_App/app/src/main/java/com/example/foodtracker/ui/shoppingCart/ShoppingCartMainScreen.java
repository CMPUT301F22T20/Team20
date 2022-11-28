package com.example.foodtracker.ui.shoppingCart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;

/**
 * This class is used to create an object that will be used to represent the Shopping Cart Main Screen
 * This class extends from {@link AppCompatActivity}
 */
public class ShoppingCartMainScreen extends AppCompatActivity {
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
}