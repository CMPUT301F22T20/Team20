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

    private TextView title;
    private EditText serving;

    private MealPlanRecipeDialogListener listener;

    public interface MealPlanRecipeDialogListener {
        void onRecipeAdd(Recipe meal_plan_add_recipe);
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
        title = view.findViewById(R.id.recipeTitle);
        serving = view.findViewById(R.id.recipeServings);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments().get("meal_plan_add_recipe") != null) {
            Bundle selectedBundle = getArguments();
            Recipe received_recipe = (Recipe) selectedBundle.get("meal_plan_add_recipe");

            return builder.setView(view).setTitle("Add Meal plan recipe")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialogInterface, i) -> addClick(received_recipe))
                    .create();
        }

        return builder.setView(view).setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null)
                .create();

    }

    public void initializeFields(Recipe recipe) {
        title.setText(recipe.getTitle());
    }

    public void addClick(Recipe recipe) {
        Recipe recipeToAdd = new Recipe();

        recipeToAdd.setTitle(recipe.getTitle());
        recipeToAdd.setCategory(recipe.getCategory());
        recipeToAdd.setPrepTime(recipe.getPrepTime());
        recipeToAdd.setIngredients(recipe.getIngredients());
        recipeToAdd.setComment(recipe.getComment());

        if (setFields(recipeToAdd)) {
            listener.onRecipeAdd(recipeToAdd);
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

}
