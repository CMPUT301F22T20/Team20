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
import com.example.foodtracker.model.Ingredient;

public class AddIngredient extends DialogFragment {

    private EditText description;
    private EditText category;
    private EditText quantity;
    private smallIngredientListener listener;

    public interface smallIngredientListener {
        void addRecipeIngredient(Ingredient new_ingredient);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof smallIngredientListener) {
            listener = (smallIngredientListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " must implement smallIngredientListener");
        }
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
        Ingredient my_ingredient = new Ingredient();

        String add_description = description.getText().toString();
        my_ingredient.setDescription(add_description);

        String add_quantity_str = quantity.getText().toString();
        try {
            int add_quantity_int = Integer.parseInt(add_quantity_str);
            my_ingredient.setAmount(add_quantity_int);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String add_category = category.getText().toString();
        my_ingredient.setCategory(add_category);

        listener.addRecipeIngredient(my_ingredient);
    }
}
