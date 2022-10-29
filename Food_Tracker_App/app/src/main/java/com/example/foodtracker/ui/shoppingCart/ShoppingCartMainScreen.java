package com.example.foodtracker.ui.shoppingCart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.ingredients.NavBar;

public class ShoppingCartMainScreen extends AppCompatActivity {

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