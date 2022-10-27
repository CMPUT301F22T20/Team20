package com.example.foodtracker.ui_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.foodtracker.MainActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Class to test the the functionality of Ingredients (Viewing, Adding, Editing and Deleting {@link com.example.foodtracker.model.Ingredient}
 * @version 1.0
 * TODO: Still have to complete the tests after complete implementation of Ingredients
 */
public class IngredientTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityRule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = activityRule.getActivity();
    }

    /**
     * Test to check and see if clicking on Item in Recycler View expands it and shows more details
     */
    @Test
    public void checkIngredientListExpandOnClick(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickInRecyclerView(0);
        assertTrue(solo.searchText("Quantity"));
        solo.clickInRecyclerView(0);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
