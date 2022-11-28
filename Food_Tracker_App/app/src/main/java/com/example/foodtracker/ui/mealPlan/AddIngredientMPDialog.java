package com.example.foodtracker.ui.mealPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.mealPlan.MealPlanDay;

public class AddIngredientMPDialog extends DialogFragment {

    private TextView description;
    private EditText amount;
    private TextView unit;
    private ImageButton closeButton;

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
        View view = getLayoutInflater().inflate(R.layout.meal_plan_scale_ingredient_dialog, null);
        description = view.findViewById(R.id.ingredientTextView);
        amount = view.findViewById(R.id.ingredientAmount);
        unit = view.findViewById(R.id.ingredientUnit);
        closeButton = view.findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        /**
         * when adding an ingredient to a meal plan
         */
        if (getArguments().get("meal_plan_add_ingredient") != null) {
            Bundle selectedBundle = getArguments();
            Ingredient received_ingredient = (Ingredient) selectedBundle.get("meal_plan_add_ingredient");

            initializeFields(received_ingredient);

            AlertDialog dialog = builder.setView(view).setTitle("Add ingredient to meal plan")
                    .setPositiveButton("Add", null)
                    .create();

            dialog.setOnShowListener(dialogInterface -> {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(v -> {
                    if (addClick(received_ingredient)) {
                        dialog.dismiss();
                    }
                });
            });

            return dialog;
        }

        /**
         * when editing the amount of ingredient in a meal plan
         */
        else {
            Bundle bundle1 = (Bundle) getArguments().get("meal_plan_edit_ingredient");
            MealPlanDay received_meal_plan = (MealPlanDay) bundle1.get("meal_plan");

            Bundle bundle2 = (Bundle) getArguments().get("edit_ingredient");
            int received_ingredient_index = (int) bundle2.get("ingredient_index");
            Ingredient received_ingredient = received_meal_plan.getIngredients().get(received_ingredient_index);

            initializeFields(received_ingredient);
            amount.setText(String.valueOf(received_ingredient.getAmount()));

            AlertDialog dialog = builder.setView(view).setTitle("Change ingredient Amount")
                    .setPositiveButton("Edit", null)
                    .create();

            dialog.setOnShowListener(dialogInterface -> {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(v -> {
                    if (editClick(received_meal_plan, received_ingredient_index)) {
                        dialog.dismiss();
                    }
                });
            });

            return dialog;

        }

    }

    public void initializeFields(Ingredient ingredient) {
        description.setText(ingredient.getDescription());
        unit.setText(ingredient.getUnit());
    }

    public Boolean addClick(Ingredient ingredient) {
        Ingredient ingredientToAdd = new Ingredient();

        ingredientToAdd.setDescription(ingredient.getDescription());
        ingredientToAdd.setUnit(ingredient.getUnit());
        ingredientToAdd.setCategory(ingredient.getCategory());
        ingredientToAdd.setExpiry(ingredient.getExpiry());
        ingredientToAdd.setLocation(ingredient.getLocation());

        if (setFields(ingredientToAdd)) {
            listener.onIngredientAdd(ingredientToAdd);
        }
        return setFields(ingredientToAdd);
    }

    public Boolean editClick(MealPlanDay meal_plan, int index) {
        Ingredient ingredient = meal_plan.getIngredients().get(index);
        if (setFields(ingredient)) {
            meal_plan.getIngredients().get(index).setAmount(ingredient.getAmount());
            listener.onIngredientEdit(meal_plan);
        }
        return setFields(ingredient);
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
            double amountDouble = Double.parseDouble(amount_str);
            if (amountDouble <= 0) {
                amount.setError("Invalid amount");
                return false;
            }
            ingredient.setAmount(amountDouble);
        } catch (NumberFormatException e) {
            amount.setError("Invalid amount");
            valid = false;
        }

        return valid;

    }

}
