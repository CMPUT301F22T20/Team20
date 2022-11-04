package com.example.foodtracker.ui_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.foodtracker.MainActivity;
import com.example.foodtracker.R;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Class to test the the functionality of Ingredients (Viewing, Adding, Editing and Deleting {@link com.example.foodtracker.model.ingredient.Ingredient}
 * @version 2.0
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

    /**
     * Test to check if the Edit button works
     * Also checks if the Cancel button works
     */
    @Test
    public void checkEditIngredientAndCancelButtons(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickInRecyclerView(0);
        solo.clickOnText("Edit");
        solo.sleep(1000);
        solo.clickOnView(solo.getView(android.R.id.button2));
        solo.sleep(1000);
    }

    /**
     * Test to add a new ingredient called Frozen Buffalo Wings and see if it appears in the list
     */
    @Test
    public void addNewIngredient() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("+");
        solo.sleep(2000);
        solo.enterText((EditText) solo.getView(R.id.ingredientDescription), " Frozen Buffalo Wings");
        solo.enterText((EditText) solo.getView(R.id.ingredientCost), "5.60");
        solo.enterText((EditText) solo.getView(R.id.ingredientQuantity), "3");
        solo.pressSpinnerItem(0,0);
        Spinner spinner = (Spinner) solo.getView(Spinner.class, 1);
        spinner.setSelection(3, true);
        solo.setDatePicker(0, 2023, 12, 30);
        solo.clickOnView(solo.getView(android.R.id.button1));
        assertTrue(solo.waitForText("Frozen Buffalo Wings"));
    }

    /**
     * Test to change the first item in the list to Oreo Thins and see if the change is reflected in the list
     */
    @Test
    public void editExistingIngredient(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickInRecyclerView(0);
        solo.clickOnText("Edit");
        solo.sleep(2000);
        solo.clearEditText(0);
        solo.clearEditText(1);
        solo.clearEditText(2);
        solo.enterText((EditText) solo.getView(R.id.ingredientDescription), "Oreo Thins");
        solo.enterText((EditText) solo.getView(R.id.ingredientCost), "1.60");
        solo.enterText((EditText) solo.getView(R.id.ingredientQuantity), "3");
        solo.pressSpinnerItem(0,0);
        Spinner spinner = (Spinner) solo.getView(Spinner.class, 1);
        spinner.setSelection(8, true);
        solo.setDatePicker(0, 2023, 12, 30);
        solo.clickOnView(solo.getView(android.R.id.button1));
        assertTrue(solo.waitForText("Oreo Thins"));
    }

    /**
     * Test to check if the delete button actually deletes the item
     */
    @Test
    public void clickOnDeleteButton(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        ArrayList<TextView> textViews = solo.clickInRecyclerView(0);
        String clicked_str = String.valueOf(textViews.get(0).getText());
        solo.clickOnText("Delete");
        solo.searchText(clicked_str);
        solo.sleep(3000);
    }

    /**
     * Test to check if the plus button in the top bar starts the add ingredient dialog fragment
     */
    @Test
    public void clickOnTopBarAddButton(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImageButton(0);
        solo.clickOnText("+");
        solo.sleep(1000);
        solo.clickOnView(solo.getView(android.R.id.button2));
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
