package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;

/**
 * This class is used to create an object that will be used to represent the Recipes Main Screen
 * This class extends from {@link AppCompatActivity}
 */
public class RecipesMainScreen extends AppCompatActivity {

    /**
     * @param savedInstanceState This is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_main);

        if (savedInstanceState == null) {
            NavBar navBar = NavBar.newInstance(MenuItem.RECIPES);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.recipes_nav_bar, navBar)
                    .commit();
        }
    }
}