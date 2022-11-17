package com.example.foodtracker.ui_test;

import static org.junit.Assert.assertTrue;

import android.widget.EditText;
import android.widget.TextView;

import com.example.foodtracker.R;
import com.example.foodtracker.ui.recipes.AddRecipeActivity;
import com.example.foodtracker.ui.recipes.RecipeDisplay;
import com.example.foodtracker.ui.recipes.RecipesMainScreen;
import com.robotium.solo.Solo;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.foodtracker.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Class to test the the functionality of Recipes (Viewing, Adding, Editing and Deleting {@link com.example.foodtracker.model.recipe.Recipe}
 * @version 1.0
 */
public class RecipeTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.navigation_recipes));
        assertTrue(solo.waitForActivity(RecipesMainScreen.class));
    }

    /**
     * Test to see if {@link RecipeDisplay} activity opens on clicking on a recipe in recycler view
     */
    @Test
    public void recipeDisplayOpenOnClick(){
        ArrayList<TextView> textViews = solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", RecipeDisplay.class);
        String clickedRecipeTitle = String.valueOf(textViews.get(0).getText());
        solo.waitForCondition(() -> solo.searchText(clickedRecipeTitle), 2000);
        assertTrue(solo.searchText("Preparation Time"));
        assertTrue(solo.searchText("Servings"));
        assertTrue(solo.searchText("Category"));
        assertTrue(solo.searchText("Ingredients"));
        assertTrue(solo.searchText("Comment"));
    }

    /**
     * Test to check the functionality of the add button in top bar
     */
    @Test
    public void checkTopBarAddButtonFunctionality(){
        solo.clickOnView(solo.getView(R.id.top_bar_add_button));
        solo.assertCurrentActivity("Wrong Activity", AddRecipeActivity.class);
    }

    /**
     * Test to check functionality of cancel button in {@link AddRecipeActivity} activity
     */
    @Test
    public void cancelAddNewRecipe(){
        checkTopBarAddButtonFunctionality();
        solo.clickOnView(solo.getView(R.id.recipes_cancel));
        solo.assertCurrentActivity("Wrong Activity", RecipesMainScreen.class);
    }

    /**
     * Test to add a new recipe called PB&J Sandwich and see if it appears in the recycler view list
     */
    @Test
    public void addNewRecipe(){
        checkTopBarAddButtonFunctionality();
        solo.enterText((EditText) solo.getView(R.id.recipeTitle), "PB&J Sandwich");
        solo.enterText((EditText) solo.getView(R.id.recipePrepTime), "2");
        solo.enterText((EditText) solo.getView(R.id.recipeServings), "1");
        solo.pressSpinnerItem(0,0);

        solo.clickOnView(solo.getView(R.id.addIngredient));
        solo.enterText((EditText) solo.getView(R.id.ingredientDescription), "Bread");
        solo.enterText((EditText) solo.getView(R.id.ingredientQuantity), "2");
        solo.enterText((EditText) solo.getView(R.id.ingredientUnit), "Slices");
        solo.pressSpinnerItem(0,0);
        solo.clickOnView(solo.getView(android.R.id.button1));
        assertTrue(solo.waitForText("Bread"));

        solo.clickOnView(solo.getView(R.id.addIngredient));
        solo.enterText((EditText) solo.getView(R.id.ingredientDescription), "Peanut Butter");
        solo.enterText((EditText) solo.getView(R.id.ingredientQuantity), "2");
        solo.enterText((EditText) solo.getView(R.id.ingredientUnit), "Teaspoons");
        solo.pressSpinnerItem(0,0);
        solo.clickOnView(solo.getView(android.R.id.button1));

        solo.clickOnView(solo.getView(R.id.addIngredient));
        solo.enterText((EditText) solo.getView(R.id.ingredientDescription), "Strawberry Jam");
        solo.enterText((EditText) solo.getView(R.id.ingredientQuantity), "2");
        solo.enterText((EditText) solo.getView(R.id.ingredientUnit), "Teaspoons");
        solo.pressSpinnerItem(0,0);
        solo.clickOnView(solo.getView(android.R.id.button1));

        solo.enterText((EditText) solo.getView(R.id.recipeComments), "Use when hungry but too tired to cook");

        solo.clickOnView(solo.getView(R.id.recipes_confirm));
        assertTrue(solo.waitForText("PB&J Sandwich"));
    }

    /**
     * Check the functionality of the cancel button when adding ingredient in {@link AddRecipeActivity} activity
     */
    @Test
    public void checkCancelAddIngredientButton(){
        checkTopBarAddButtonFunctionality();
        solo.clickOnView(solo.getView(R.id.addIngredient));
        solo.clickOnView(solo.getView(android.R.id.button2));
    }

    /**
     * Test to edit a recipe
     * TODO: Needs to be completed
     */
    @Test
    public void editRecipe(){
    }

    /**
     * Test to delete a recipe and check if it is removed from the recycler view list
     */
    @Test
    public void deleteRecipe(){
        ArrayList<TextView> textViews = solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong Activity", RecipeDisplay.class);
        String clickedRecipeTitle = String.valueOf(textViews.get(0).getText());
        solo.waitForCondition(() -> solo.searchText(clickedRecipeTitle), 2000);
        solo.clickOnView(solo.getView(R.id.recipeDeleteButton));
        solo.waitForCondition(() -> !solo.searchText(clickedRecipeTitle), 2000);
    }

    /**
     * Test to check the functionality of the back button in the top bar
     */
    @Test
    public void testTopBarBackButton(){
        solo.clickOnView(solo.getView(R.id.top_bar_back_button));
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
