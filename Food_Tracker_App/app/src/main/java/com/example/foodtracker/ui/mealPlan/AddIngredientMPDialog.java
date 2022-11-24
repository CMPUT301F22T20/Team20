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
import com.example.foodtracker.ui.ingredients.dialogs.IngredientDialog;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.List;

public class AddIngredientMPDialog extends DialogFragment {

    private TextView description;
    private EditText amount;
    private TextView unit;

    private MealPlanIngredientDialogListener listener;

    public interface MealPlanIngredientDialogListener {
        void onIngredientAdd(Ingredient meal_plan_add_ingredient);
        void onIngredientEdit(MealPlanDay meal_plan_edit_ingredient);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MealPlanIngredientDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + MealPlanIngredientDialogListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.add_ingredient_meal_plan_dialog, null);
        description = view.findViewById(R.id.ingredientTextView);
        amount = view.findViewById(R.id.ingredientAmount);
        unit = view.findViewById(R.id.ingredientUnit);



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments().get("meal_plan_add_ingredient") != null) {
            Bundle selectedBundle = getArguments();
            Ingredient received_ingredient = (Ingredient) selectedBundle.get("meal_plan_add_ingredient");
            initializeFields(received_ingredient);
            return builder.setView(view).setTitle("Add Meal plan ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Add", (dialogInterface, i) -> addClick(received_ingredient))
                    .create();
        }

        if (getArguments().get("meal_plan_edit_ingredient") != null) {
            Bundle bundle1 = (Bundle) getArguments().get("meal_plan_edit_ingredient");
            MealPlanDay received_meal_plan = (MealPlanDay) bundle1.get("meal_plan");

            Bundle bundle2 = (Bundle) getArguments().get("edit_ingredient");
            int received_ingredient_index = (int) bundle2.get("ingredient_index");
            Ingredient received_ingredient = received_meal_plan.getIngredients().get(received_ingredient_index);

            initializeFields(received_ingredient);
            amount.setText(String.valueOf(received_ingredient.getAmount()));
            return builder.setView(view).setTitle("Edit Ingredient Amount")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Edit",
                            (dialogInterface, i) -> editClick(received_meal_plan, received_ingredient_index))
                    .create();
        }

        return builder.setView(view).setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null)
                .create();

    }

    public void initializeFields(Ingredient ingredient) {
        description.setText(ingredient.getDescription());
        unit.setText(ingredient.getUnit());
    }

    public void addClick(Ingredient ingredient) {
        Ingredient ingredientToAdd = new Ingredient();

        ingredientToAdd.setDescription(ingredient.getDescription());
        ingredientToAdd.setUnit(ingredient.getUnit());
        ingredientToAdd.setCategory(ingredient.getCategory());
        ingredientToAdd.setExpiry(ingredient.getExpiry());
        ingredientToAdd.setLocation(ingredient.getLocation());

        if (setFields(ingredientToAdd)) {
            listener.onIngredientAdd(ingredientToAdd);
        }
    }

    public void editClick(MealPlanDay meal_plan, int index) {
        Ingredient ingredient = meal_plan.getIngredients().get(index);
        if (setFields(ingredient)) {
            meal_plan.getIngredients().get(index).setAmount(ingredient.getAmount());
            listener.onIngredientEdit(meal_plan);
        }
    }


    /**
     * Checking if the input amount field of the ingredient valid
     * @param ingredient
     * @return {@link Boolean} valid return true if the amount is int and greater than 0
     */
    public Boolean setFields(Ingredient ingredient) {
        Boolean valid = true;

        String amount_str = amount.getText().toString();
        try {
            int amountInt = Integer.parseInt(amount_str);
            if (amountInt <= 0) {
                amount.setError("Invalid amount");
                return false;
            }
            ingredient.setAmount(amountInt);
        } catch (NumberFormatException e) {
            amount.setError("Invalid amount");
            valid = false;
        }

        return valid;

    }

}
