package com.example.foodtracker.ui.ingredients.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodtracker.R;

/**
 * An object of this class represents the dialog fragment that allows user to choose between adding a new ingredient, ingredient location or ingredient category
 * This class extends from {@link DialogFragment}
 */
public class AddDialog extends DialogFragment {

    public static final String ADD_INGREDIENT_SELECTION_TAG = "Add_ingredient";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.ingredients_add_selection_dialog, null);
        Button ingredientButton = view.findViewById(R.id.addIngredientSelectionButton);
        Button locationButton = view.findViewById(R.id.addIngredientLocationSelectionButton);
        Button categoryButton = view.findViewById(R.id.addIngredientCategorySelectionButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ingredientButton.setOnClickListener(v -> {
            dismiss();
            new IngredientDialog().show(getParentFragmentManager(), "Add_ingredient");
        });
        locationButton.setOnClickListener(v -> {
            dismiss();
            new AddLocationDialog().show(getParentFragmentManager(), "Add_location");
        });
        categoryButton.setOnClickListener(v -> {
            dismiss();
            new AddCategoryDialog().show(getParentFragmentManager(), "Add_Category");
        });

        return builder
                .setView(view)
                .setTitle("What would you like to add?")
                .setNegativeButton("Cancel", null)
                .create();
    }
}
