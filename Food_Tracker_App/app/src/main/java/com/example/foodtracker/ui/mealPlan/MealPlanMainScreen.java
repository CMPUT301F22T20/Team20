package com.example.foodtracker.ui.mealPlan;

import static com.example.foodtracker.ui.mealPlan.AddMealPlanDialog.CREATE_MEAL_PLAN_TAG;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.MenuItem;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealPlan.MealPlanDay;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.NavBar;
import com.example.foodtracker.ui.TopBar;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class is used to create an object that is used to represent the main screen for Meal Plan
 * This class extends from {@link AppCompatActivity}
 * @version 1.0
 * @see <a href=https://www.geeksforgeeks.org/how-to-create-a-nested-recyclerview-in-android</a>
 */
public class MealPlanMainScreen extends AppCompatActivity implements TopBar.TopBarListener,
        MealPlanDayRecyclerViewAdapter.MealPlanDayArrayListener, createMealPlanDialog.setMPDatesListener,
        singleMealPlanDialog.setSingleMPDatesListener
{
    private final Collection<MealPlanDay> mealPlanDaysCollection = new Collection<>(MealPlanDay.class, new MealPlanDay());
    private final ArrayList<MealPlanDay> mealPlanDayArrayList = new ArrayList<>();
    private final MealPlanDayRecyclerViewAdapter adapter = new MealPlanDayRecyclerViewAdapter(this, mealPlanDayArrayList);


    public MealPlanMainScreen() {
        super(R.layout.meal_plan_main);
    }

    /**
     * @param savedInstanceState this is of type {@link Bundle}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_plan_main);
         initializeData();

        if (savedInstanceState == null) {
          //  createData();  TODO: REMOVE LATER
            createRecyclerView();
            createNavBar();
            createTopBar();
        }
    }

    /**
     * Adds some initial data to the list
     */

    private void initializeData() {
         mealPlanDaysCollection.getAll(list -> {
         mealPlanDayArrayList.addAll(list);
         adapter.notifyItemRangeInserted(0, mealPlanDayArrayList.size());
        });
    }


    //TODO Delete createData() - this was used for being able to add a new meal plan object for testing

/*
    private void createData() {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ArrayList<SimpleIngredient> simpleIngredient = new ArrayList<>();

       // SimpleIngredient sIng1 = new SimpleIngredient("Apple","KILOGRAM","PANTRY","SHELF",1,"01-01-2022");
       // simpleIngredient.add(sIng1);

        Ingredient ing1 = new Ingredient("Cheese","KILOGRAM","PANTRY","SHELF",1,"01-01-2022");
        Ingredient ing2 = new Ingredient("Chocolate","KILOGRAM","PANTRY","SHELF",1,"01-01-2022");

        ingredientArrayList.add(ing1);
        ingredientArrayList.add(ing2);

        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        Recipe rec1  = new Recipe("", "Ice Cream",20,2,"DINNER","",simpleIngredient);
        Recipe rec2  = new Recipe("", "Pie",22,2,"DINNER","",simpleIngredient);
        Recipe rec3 =  new Recipe("", "Cheesecake",22,2,"DINNER","",simpleIngredient);
        recipeArrayList.add(rec1);
        recipeArrayList.add(rec2);
        recipeArrayList.add(rec3);

        MealPlanDay mealPlanDay = new MealPlanDay("11-20-2022", ingredientArrayList, recipeArrayList);
        mealPlanDayArrayList.add(mealPlanDay);


        mealPlanDaysCollection.createDocument(mealPlanDay, () -> {
            adapter.notifyItemInserted(mealPlanDayArrayList.indexOf(mealPlanDay));
        });

    } */
    private void createRecyclerView() {
        RecyclerView mealPlanRecyclerView = findViewById(R.id.mealPlanDays);
        mealPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mealPlanRecyclerView.setAdapter(adapter);


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
    public void deleteMealPlan(MealPlanDay mealPlanDay) {
        int removedIndex = mealPlanDayArrayList.indexOf(mealPlanDay);
        mealPlanDayArrayList.remove(removedIndex);


        mealPlanDaysCollection.delete(mealPlanDay, () ->
        adapter.notifyItemRemoved(removedIndex));
    }

    /**
     * Deletes an ingredient from a meal plan
     * @param ingredientPosition
     * @param mealPlan
     */

    @Override
    public void deleteIngredient(int ingredientPosition, MealPlanDay mealPlan) {
        mealPlanDaysCollection.updateDocument(mealPlan, () -> adapter.notifyDataSetChanged());
    }


    @Override
    public void onAddClick() {
        new AddMealPlanDialog().show(getSupportFragmentManager(), CREATE_MEAL_PLAN_TAG);
    }

    /**
     * deletes a recipe from the meal plan
     * @param recipePosition
     * @param mealPlan
     */
    @Override
    public void deleteRecipe(int recipePosition, MealPlanDay mealPlan) {
        mealPlanDaysCollection.updateDocument(mealPlan, () -> adapter.notifyDataSetChanged());
    }

    @Override
    public void addMP(String dates) {

        //TODO clear the database when there are items

        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        MealPlanDay mealPlanDay = new MealPlanDay(dates, ingredientArrayList, recipeArrayList);


        mealPlanDayArrayList.add(mealPlanDay);
        mealPlanDaysCollection.createDocument(mealPlanDay, () -> {
                    adapter.notifyItemInserted(ingredientArrayList.indexOf(mealPlanDay));
                }
        );
    }

    @Override
    public void addSingle(String day) {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        MealPlanDay mealPlanDay = new MealPlanDay(day, ingredientArrayList, recipeArrayList);

        mealPlanDayArrayList.add(mealPlanDay);
    }

    @Override
    public boolean isInList(String day) {
    //TODO implement check to make sure there are no duplicates
        return false;
    }

}