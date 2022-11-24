package com.example.foodtracker.ui.mealPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.ui.ingredients.IngredientsMainScreen;

public class AddMealPlanDialog extends DialogFragment implements createMealPlanDialog.createMealPlanDialogListener{

    public static final String CREATE_MEAL_PLAN_TAG = "Create_meal_plan";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.meal_plan_selection_dialog, null);
        Button createPlanButton = view.findViewById(R.id.createMealPlan);
        Button ingredientButton = view.findViewById(R.id.addMealIngredient);
        Button recipeButton = view.findViewById(R.id.addMealRecipe);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        createPlanButton.setOnClickListener(v -> {
            dismiss();
            new createMealPlanDialog().show(getParentFragmentManager(), "Create_meal_plan");
        });

        ingredientButton.setOnClickListener(v -> {
            dismiss();
            Intent ingredientsIntent = new Intent(view.getContext(), IngredientsMainScreen.class);
            startActivity(ingredientsIntent);
        });
        /*
        categoryButton.setOnClickListener(v -> {
            dismiss();
            new AddCategoryDialog().show(getParentFragmentManager(), "Add_Category");
        });
        */

        return builder
                .setView(view)
                .setTitle("Create your meal plan")
                .setNegativeButton("Cancel", null)
                .create();
    }
}
