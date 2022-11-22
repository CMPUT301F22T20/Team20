package com.example.foodtracker.ui.mealPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealPlan.MealPlanDay;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeMPDialog extends DialogFragment {

    private EditText serving;
    private MealPlanDay mealPlanDay;

    /**
     * This is a private final variable
     * This holds a collection of {@link Ingredient} objects and is of type {@link Ingredient}
     */
    private final Collection<Recipe> recipesCollection = new Collection<>(Recipe.class, new Recipe());
    private final List<String> recipeNames = new ArrayList<>();
    private ArrayAdapter<String> recipeSpinnerAdapter;
    private Spinner description;

    private MealPlanRecipeDialogListener listener;

    public interface MealPlanRecipeDialogListener {
        void onRecipeAdd(MealPlanDay meal_plan_add_ingredient);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MealPlanRecipeDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + MealPlanRecipeDialogListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.add_recipe_meal_plan_dialog, null);
        description = view.findViewById(R.id.recipeSpinner);
        serving = view.findViewById(R.id.recipeServings);


        recipeSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, recipeNames);
        description.setAdapter(recipeSpinnerAdapter);
        getRecipes(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments().get("meal_plan_add_recipe") != null) {
            Bundle selectedBundle = getArguments();
            mealPlanDay = (MealPlanDay) selectedBundle.get("meal_plan_add_recipe");

            return builder.setView(view).setTitle("Add Meal plan recipe")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialogInterface, i) -> addClick(mealPlanDay))
                    .create();
        }

        return builder.setView(view).setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null)
                .create();

    }

    public void addClick(MealPlanDay mealPlanADD) {
        Recipe recipeToAdd = new Recipe();
        String recipe_name = description.getSelectedItem().toString();
        recipeToAdd.setTitle(recipe_name);

        int position = description.getSelectedItemPosition();

        if (setFields(recipeToAdd)) {
            mealPlanADD.getRecipes().add(recipeToAdd);
            listener.onRecipeAdd(mealPlanADD);
        }
    }

    /**
     * Checking if the input amount field of the recipe valid
     * @param recipe
     * @return {@link Boolean} valid return true if the amount is int and greater than 0
     */
    public Boolean setFields(Recipe recipe) {
        Boolean valid = true;

        String amount_str = serving.getText().toString();
        try {
            int amountInt = Integer.parseInt(amount_str);
            if (amountInt <= 0) {
                serving.setError("Invalid amount");
                return false;
            }
            recipe.setServings(amountInt);
        } catch (NumberFormatException e) {
            serving.setError("Invalid amount");
            valid = false;
        }

        return valid;

    }

    /**
     * Retrieves recipes from firestore and populates a string array with the content
     */
    private void getRecipes(@Nullable Recipe edit_recipe) {
        recipesCollection.getAll(list -> {
            for (Recipe recipe : list) {
                recipeNames.add(recipe.getTitle());
                recipeSpinnerAdapter.notifyDataSetChanged();
            }
            if (edit_recipe != null) {
                description.setSelection(recipeSpinnerAdapter.getPosition(edit_recipe.getTitle()));
            }
        });
    }
}
