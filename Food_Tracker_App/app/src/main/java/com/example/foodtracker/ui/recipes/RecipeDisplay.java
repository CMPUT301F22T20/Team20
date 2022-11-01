package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_display);

        if (savedInstanceState == null) {
            createNavbar();
        }
    }

    private void createNavbar() {
        NavBar navBar = NavBar.newInstance(MenuItem.RECIPES);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.recipe_display_nav_bar, navBar)
                .commit();
    }
}