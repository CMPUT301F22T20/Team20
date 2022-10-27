package com.example.foodtracker.ui_test;

import android.app.Activity;

import com.example.foodtracker.ui.mealPlan.MealPlanMainScreen;
import com.example.foodtracker.ui.recipes.RecipesMainScreen;
import com.example.foodtracker.ui.shoppingCart.ShoppingCartMainScreen;
import com.robotium.solo.Solo;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.foodtracker.MainActivity;
import com.example.foodtracker.ui.ingredients.IngredientsMainScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Class to test the functionality of the navigation bar at the bottom of the screen
 * @version 1.0
 */
public class NavigationBarTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityRule.getActivity());
    }

    /**
     * Check if app starts
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = activityRule.getActivity();
    }

    /**
     * Check to see if clicking on Ingredients icon in the navigation bar works and brings us to Ingredients Main Screen
     */
    @Test
    public void clickOnIngredientsIconInNavBar(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("Wrong Activity", IngredientsMainScreen.class);
        solo.sleep(3000);
    }

    /**
     * Check to see if clicking back button in Ingredients Main Screen brings us back to Main Activity
     */

    @Test
    public void clickOnBackButtonInIngredientsMain(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("Wrong Activity", IngredientsMainScreen.class);
        solo.clickOnButton("BACK");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    /**
     * Check if clicking Shopping Cart Icon in Navigation Bar brings us to Shopping Cart Main Activity
     */
    @Test
    public void clickOnShoppingCartButtonInNavBar(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(1);
        solo.assertCurrentActivity("Wrong Activity", ShoppingCartMainScreen.class);
    }

    /**
     * Check if clicking on Recipes Icon in Navigation Bar brings us to Recipes Main Activity
     */
    @Test
    public void clickOnRecipesButtonInNavBar(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(2);
        solo.assertCurrentActivity("Wrong Activity", RecipesMainScreen.class);
    }

    /**
     * Check if clicking on Meal Plan Icon in Navigation Bar brings us to Meal Plan Main Activity
     */
    @Test
    public void clickOnMealPlanButtonInNavBar(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(3);
        solo.assertCurrentActivity("Wrong Activity", MealPlanMainScreen.class);
    }

    @Test
    public void MoveBetweenActivities(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("Wrong Activity", IngredientsMainScreen.class);
        solo.clickOnImageButton(1);
        solo.assertCurrentActivity("Wrong Activity", ShoppingCartMainScreen.class);
        solo.clickOnImageButton(2);
        solo.assertCurrentActivity("Wrong Activity", RecipesMainScreen.class);
        solo.clickOnImageButton(3);
        solo.assertCurrentActivity("Wrong Activity", MealPlanMainScreen.class);
    }
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
