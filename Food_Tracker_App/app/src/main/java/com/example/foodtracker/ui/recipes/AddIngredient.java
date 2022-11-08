package com.example.foodtracker.ui.recipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.List;

public class AddIngredient extends DialogFragment {

    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();
    private EditText description;
    private EditText quantity;
    private smallIngredientListener listener;
    private Spinner category;
    private ArrayAdapter<String> categoryAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof smallIngredientListener) {
            listener = (smallIngredientListener) context;
        } else {
            throw new RuntimeException(context + " must implement smallIngredientListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.smaller_add_ingredient_dialog, null);
        description = view.findViewById(R.id.ingredientDescription);
        quantity = view.findViewById(R.id.ingredientQuantity);
        category = view.findViewById(R.id.ingredientCategory);
        categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        category.setAdapter(categoryAdapter);
        getCategories();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ADD", null).create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (addClick()) {
                    dialog.dismiss();
                }
            });
        });
        return dialog;
    }

    /**
     * Retrieves categories from firestore and populates a string array with the content
     */
    private void getCategories() {
        categoryCollection.getAll(list -> {
            for (Category category : list) {
                categories.add(category.getName());
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Add an ingredient to a recipe, returns true if the added ingredient is valid
     * and false otherwise
     */
    private boolean addClick() {
        Ingredient ingredient = new Ingredient();
        boolean valid = true;

        String addDescription = description.getText().toString();
        ingredient.setDescription(addDescription);
        if (addDescription.isEmpty()) {
            description.setError("Description must not be empty");
            valid = false;
        }

        String addQuantityStr = quantity.getText().toString();
        try {
            int addQuantityInt = Integer.parseInt(addQuantityStr);
            ingredient.setAmount(addQuantityInt);
        } catch (NumberFormatException e) {
            quantity.setError("Invalid amount");
            valid = false;
        }

        if (category.getSelectedItem() == null) {
            valid = false;
        } else {
            String addCategory = category.getSelectedItem().toString();
            ingredient.setCategory(addCategory);
        }

        if (valid) {
            listener.addRecipeIngredient(ingredient);
        }
        return valid;
    }

    public interface smallIngredientListener {
        void addRecipeIngredient(Ingredient new_ingredient);
    }
}
