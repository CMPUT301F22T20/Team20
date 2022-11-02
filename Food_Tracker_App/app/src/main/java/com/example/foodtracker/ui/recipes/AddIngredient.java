package com.example.foodtracker.ui.recipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;

public class AddIngredient extends DialogFragment {

    private EditText description;
    private EditText category;
    private EditText quantity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.smaller_add_ingredient_dialog, null);
        description = view.findViewById(R.id.ingredient_Description);
        category = view.findViewById(R.id.ingredient_Category);
        quantity = view.findViewById(R.id.ingredient_Quantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addClick();
                    }
                }).create();
    }

    private void addClick() {
        String add_description = description.getText().toString();
        String add_quantity_str = quantity.getText().toString();
        String add_category = category.getText().toString();
    }
}
