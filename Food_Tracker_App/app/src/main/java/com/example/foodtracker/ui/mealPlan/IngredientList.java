package com.example.foodtracker.ui.mealPlan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealPlan.MealPlanDay;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

public class IngredientList extends AppCompatActivity implements
        AddIngredientMPDialog.MealPlanIngredientDialogListener{

    ListView ingredientListView;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayAdapter<Ingredient> adapter;
    MealPlanDay mealPlan;
    /**
     * This is a private final variable
     * This holds a collection of {@link Ingredient} objects and is of type {@link Ingredient}
     */
    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientArrayList = new ArrayList<>();
        adapter = new IngredientListAdapter(this, ingredientArrayList);
        ingredientListView.setAdapter(adapter);
        getIngredients();

        Intent intent = getIntent();
        mealPlan= (MealPlanDay) intent.getSerializableExtra("meal_plan_for_ingredient_add");

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingredient add_ingredient = adapter.getItem(i);

                Bundle args = new Bundle();
                args.putSerializable("meal_plan_add_ingredient", add_ingredient);

                AddIngredientMPDialog addIngredientMPDialog = new AddIngredientMPDialog();
                addIngredientMPDialog.setArguments(args);
                addIngredientMPDialog.show(getSupportFragmentManager(), "ADD_MEAL_PLAN_INGREDIENT");

            }
        });
    }

    /**
     * Retrieves ingredients from firestore and populates a string array with the content
     */
    private void getIngredients() {
        ingredientsCollection.getAll(list -> {
            for (Ingredient ingredient : list) {
                ingredientArrayList.add(ingredient);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onIngredientAdd(Ingredient meal_plan_add_ingredient) {
        mealPlan.getIngredients().add(meal_plan_add_ingredient);
        Intent intent1 = new Intent(getApplicationContext(), MealPlanMainScreen.class);
        intent1.putExtra("meal_plan_after_ingredient_add", mealPlan);
        startActivity(intent1);
    }
}