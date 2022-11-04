package com.example.foodtracker.ui.ingredients.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;

public class AddDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.ingredients_add_selection_dialog, null);
        Button ingredientButton = view.findViewById(R.id.addIngredientSelectionButton);
        Button locationButton = view.findViewById(R.id.addIngredientLocationSelectionButton);
        Button categoryButton = view.findViewById(R.id.addIngredientCategorySelectionButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ingredientButton.setOnClickListener(v -> new IngredientDialog().show(getChildFragmentManager(), "Add_ingredient"));
        locationButton.setOnClickListener(v -> new AddLocationDialog().show(getChildFragmentManager(), "Add_location"));
        categoryButton.setOnClickListener(v -> new AddCategoryDialog().show(getChildFragmentManager(), "Add_Category"));

        return builder
                .setView(view)
                .setTitle("What would you like to add?")
                .setNegativeButton("Cancel", null)
                .create();
    }
}
