package com.example.foodtracker.ui.ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDialog extends DialogFragment {

    private Button ingredientButton;
    private Button locationButton;
    private Button categoryButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.add_selection_dialog, null);

        ingredientButton = view.findViewById(R.id.addIngredientSelectionButton);
        locationButton = view.findViewById(R.id.addIngredientLocationSelectionButton);
        categoryButton = view.findViewById(R.id.addIngredientCategorySelectionButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ingredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddIngredientDialog().show(getChildFragmentManager(), "Add_ingredient");
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddLocationDialog().show(getChildFragmentManager(), "Add_location");
            }
        });

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddIngredientCategoryDialog().show(getChildFragmentManager(), "Add_Category");
            }
        });

        return builder
                .setView(view)
                .setTitle("Select Item to Add")
                .setNegativeButton("Cancel", null)
                .create();
    }
}
