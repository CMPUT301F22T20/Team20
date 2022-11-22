package com.example.foodtracker.ui.mealPlan;

import static com.example.foodtracker.ui.mealPlan.AddMealPlanDialog.CREATE_MEAL_PLAN_TAG;

import android.os.Bundle;
import android.util.Log;

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
import com.example.foodtracker.ui.ingredients.dialogs.IngredientDialog;
import com.example.foodtracker.ui.recipes.RecipeRecyclerViewAdapter;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

/**
 * This class is used to create an object that is used to represent the main screen for Meal Plan
 * This class extends from {@link AppCompatActivity}
 * @version 1.0
 * @see <a href=https://www.geeksforgeeks.org/how-to-create-a-nested-recyclerview-in-android</a>
 */
public class MealPlanMainScreen extends AppCompatActivity implements
        TopBar.TopBarListener,
        MealPlanDayRecyclerViewAdapter.MealPlanArrayListener,
        AddIngredientMPDialog.MealPlanDialogListener,
        AddRecipeMPDialog.MealPlanRecipeDialogListener {
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
            //createData();
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

    private void createData() {
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ingredientArrayList.add(new Ingredient("apple", "", "pantry", "snack", 2, ""));
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        recipeArrayList.add(new Recipe("", "Soup", 90, 6, "Dinner", "", ingredientArrayList));
        MealPlanDay mealPlanDay = new MealPlanDay("11-20-2022", ingredientArrayList, recipeArrayList);
        mealPlanDayArrayList.add(mealPlanDay);
        mealPlanDaysCollection.createDocument(mealPlanDay, () -> {
            adapter.notifyItemInserted(mealPlanDayArrayList.indexOf(mealPlanDay));
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


    @Override
    public void onAddClick() {
        new AddMealPlanDialog().show(getSupportFragmentManager(), CREATE_MEAL_PLAN_TAG);
    }


    @Override
    public void onAddIngredientClick(MealPlanDay mealPlanDay) {
        Bundle args = new Bundle();
        args.putSerializable("meal_plan_add_ingredient", mealPlanDay);

        AddIngredientMPDialog addIngredientMPDialog = new AddIngredientMPDialog();
        addIngredientMPDialog.setArguments(args);
        addIngredientMPDialog.show(getSupportFragmentManager(), "ADD_MEAL_PLAN_INGREDIENT");
    }

    @Override
    public void onAddRecipeClick(MealPlanDay mealPlan) {
        Bundle args = new Bundle();
        args.putSerializable("meal_plan_add_recipe", mealPlan);

        AddRecipeMPDialog addRecipeMPDialog = new AddRecipeMPDialog();
        addRecipeMPDialog.setArguments(args);
        addRecipeMPDialog.show(getSupportFragmentManager(), "ADD_MEAL_PLAN_RECIPE");
    }

    @Override
    public void onIngredientAdd(MealPlanDay meal_plan_add_ingredient) {
        int editIndex = mealPlanDayArrayList.indexOf(meal_plan_add_ingredient);
        mealPlanDaysCollection.updateDocument(meal_plan_add_ingredient, () -> adapter.notifyItemChanged(editIndex));
    }

    @Override
    public void onRecipeAdd(MealPlanDay meal_plan_add_recipe) {
        int editIndex = mealPlanDayArrayList.indexOf(meal_plan_add_recipe);
        mealPlanDaysCollection.updateDocument(meal_plan_add_recipe, () -> adapter.notifyItemChanged(editIndex));
    }
}