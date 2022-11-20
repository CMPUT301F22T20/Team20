package com.example.foodtracker.ui.mealPlan;

import static com.example.foodtracker.ui.mealPlan.AddMealPlanDialog.CREATE_MEAL_PLAN_TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;

/**
 * This class is used to create an object that is used to represent the main screen for Meal Plan
 * This class extends from {@link AppCompatActivity}
 * @version 1.0
 */
public class MealPlanMainScreen extends AppCompatActivity implements TopBar.TopBarListener {




    /**
     * @param savedInstanceState this is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_main);


        if (savedInstanceState == null) {
            createNavBar();
            createTopBar();
        }
    }

    private void createNavBar(){
        NavBar navBar = NavBar.newInstance(MenuItem.MEAL_PLANNER);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.meal_plan_nav_bar, navBar)
                .commit();
    }
    private void createTopBar(){
        TopBar topBar = TopBar.newInstance("Meal Plan", true, false);
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.topBarContainerView, topBar).commit();

    }


    @Override
    public void onAddClick() {
        new AddMealPlanDialog().show(getSupportFragmentManager(), CREATE_MEAL_PLAN_TAG);
    }


}