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
import com.example.foodtracker.ui.Sort;
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

    private Sort<MealPlanDay.FieldName,MealPlanDayRecyclerViewAdapter,MealPlanDay> sort;


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
        initializeSort();


        if (savedInstanceState == null){
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

    /**
     * Deletes a single {@link MealPlanDay} object from the meal plan.
     */
    @Override
    public void deleteMealPlan(MealPlanDay mealPlanDay) {
        int removedIndex = mealPlanDayArrayList.indexOf(mealPlanDay);
        mealPlanDayArrayList.remove(removedIndex);

        mealPlanDaysCollection.delete(mealPlanDay, () ->
        adapter.notifyDataSetChanged());
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

    /**
     * Creates multiple {@link MealPlanDay} objects all at once, given a list of values.
     * @param listOfDates
     */

    @Override
    public void addMP(ArrayList<String> listOfDates) {

        ArrayList<Ingredient> ingredientArrayList1 = new ArrayList<>();
        ArrayList<Recipe> recipeArrayList1 = new ArrayList<>();


        for (MealPlanDay clearMealPlanDay: mealPlanDayArrayList){
            mealPlanDaysCollection.delete(clearMealPlanDay, () -> {});
        }

        if (!mealPlanDayArrayList.isEmpty()){
            mealPlanDayArrayList.clear();
            adapter.notifyDataSetChanged();
        }

        for (String dates: listOfDates){
            MealPlanDay mealPlanDay = new MealPlanDay(dates, ingredientArrayList1, recipeArrayList1);
            mealPlanDayArrayList.add(mealPlanDay);
            mealPlanDaysCollection.createDocument(mealPlanDay, () ->
                    { adapter.notifyDataSetChanged();
                        sort.sortByFieldName();}
            );

        }
    }

    /**
     * Creates a single {@link MealPlanDay} object.
     * @param day
     */

    @Override
    public void addSingle(String day) {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        MealPlanDay mealPlanDay = new MealPlanDay(day, ingredientArrayList, recipeArrayList);

        mealPlanDayArrayList.add(mealPlanDay);
        mealPlanDaysCollection.createDocument(mealPlanDay, () ->
                {
                    adapter.notifyItemInserted(mealPlanDayArrayList.lastIndexOf(mealPlanDay));
                    sort.sortByFieldName();
                }
        );
    }

    /**
     * Checks if user input is in current meal plan.
     * @param day
     * @return
     */
    @Override
    public boolean isInList(String day) {
        ArrayList<String> mealPlanDays = new ArrayList<>();

        for (MealPlanDay meal: mealPlanDayArrayList){
                mealPlanDays.add(meal.getDay());
        }
        if (mealPlanDays.contains(day)){
            return true;
        }
        return false;
    }

    private void initializeSort() {
        sort = new Sort<>(this.mealPlanDaysCollection, this.adapter, this.mealPlanDayArrayList, MealPlanDay.FieldName.class);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.sort_spinnerMP, sort)
                .commit();
    }


}