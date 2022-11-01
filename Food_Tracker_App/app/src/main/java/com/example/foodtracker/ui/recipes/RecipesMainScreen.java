package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;

public class RecipesMainScreen extends AppCompatActivity {

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