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

    private EditText amount;
    private TextView unit;
    private MealPlanDay mealPlanDay;

    /**
     * This is a private final variable
     * This holds a collection of {@link Ingredient} objects and is of type {@link Ingredient}
     */
    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());
    private final List<String> ingredientNames = new ArrayList<>();
    private ArrayAdapter<String> ingredientSpinnerAdapter;
    private Spinner description;

    private MealPlanDialogListener listener;

    public interface MealPlanDialogListener {
        void onIngredientAdd(MealPlanDay meal_plan_add_ingredient);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (MealPlanDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + MealPlanDialogListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.add_ingredient_meal_plan_dialog, null);
        description = view.findViewById(R.id.ingredientSpinner);
        amount = view.findViewById(R.id.ingredientAmount);
        unit = view.findViewById(R.id.ingredientUnit);

        ingredientSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ingredientNames);
        description.setAdapter(ingredientSpinnerAdapter);
        getIngredients(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments().get("meal_plan_add_ingredient") != null) {
            Bundle selectedBundle = getArguments();
            mealPlanDay = (MealPlanDay) selectedBundle.get("meal_plan_add_ingredient");

            return builder.setView(view).setTitle("Add Meal plan ingredient")
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
        Ingredient ingredientToAdd = new Ingredient();
        String ingredient_name = description.getSelectedItem().toString();
        ingredientToAdd.setDescription(ingredient_name);

        int position = description.getSelectedItemPosition();

        if (setFields(ingredientToAdd)) {
            mealPlanADD.getIngredients().add(ingredientToAdd);
            listener.onIngredientAdd(mealPlanADD);
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

    /**
     * Retrieves categories from firestore and populates a string array with the content
     */
    private void getIngredients(@Nullable Ingredient edit_ingredient) {
        ingredientsCollection.getAll(list -> {
            for (Ingredient ingredient : list) {
                ingredientNames.add(ingredient.getDescription());
                ingredientSpinnerAdapter.notifyDataSetChanged();
            }
            if (edit_ingredient != null) {
                description.setSelection(ingredientSpinnerAdapter.getPosition(edit_ingredient.getDescription()));
                unit.setText(edit_ingredient.getUnit());
            }
        });
    }
}
