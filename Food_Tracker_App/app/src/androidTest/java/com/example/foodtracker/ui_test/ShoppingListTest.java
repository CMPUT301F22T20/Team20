package com.example.foodtracker.ui_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.foodtracker.MainActivity;
import com.example.foodtracker.*;
import com.example.foodtracker.ui.shoppingCart.ShoppingCartMainScreen;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Class to test the functionality of Shopping Cart
 */
public class ShoppingListTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.navigation_shopping));
        assertTrue(solo.waitForActivity(ShoppingCartMainScreen.class));
        solo.waitForText("SNACKS");
    }

    @Test
    public void testDropDownExpandOnClick(){
        solo.clickOnView(solo.getView(R.id.shopping_category_name));
        assertTrue(solo.searchText("x"));
    }

    /**
     * Test to see if checking the box removes the ingredient from shopping list and moves it to Ingredients
     */
    @Test
    public void selectIngredientFromList(){
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.shopping_check_box));
        assertFalse(solo.searchText("Cheese"));
        solo.clickOnView(solo.getView(R.id.top_bar_back_button));
        solo.clickOnView(solo.getView(R.id.navigation_ingredients));
        assertTrue(solo.searchText("Cheese"));
        solo.clickOnText("Cheese");
        solo.clickOnView(solo.getView(R.id.delete_ingredient));
        assertFalse(solo.searchText("Cheese"));
    }

    @Test
    public void testSortByCategory(){
        solo.pressSpinnerItem(0,0);
        ArrayList<TextView> textView = solo.clickInList(0);
        String clickedRecipeTitle = String.valueOf(textView.get(0).getText());

        solo.clickOnView(solo.getView(R.id.sorting_direction));
        solo.sleep(1000);

        ArrayList<TextView> textView1 = solo.clickInList(0);
        String clickedRecipeTitle2 = String.valueOf(textView1.get(0).getText());
        assertNotEquals(clickedRecipeTitle, clickedRecipeTitle2);
    }

    @Test
    public void testSortByDescription(){
        solo.pressSpinnerItem(0,1);
        solo.clickInList(0);

    }

}
