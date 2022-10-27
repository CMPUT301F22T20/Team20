package com.example.foodtracker.ui.mealPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;


public class MealPlanMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_main);

        if (savedInstanceState == null) {
            NavBar navBar = NavBar.newInstance(MenuItem.MEAL_PLANNER);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.meal_plan_nav_bar, navBar)
                    .commit();
        }
    }
}