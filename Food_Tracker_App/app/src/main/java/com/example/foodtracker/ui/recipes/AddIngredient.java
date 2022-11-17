package com.example.foodtracker.ui.recipes;

import static androidx.fragment.app.FragmentManager.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

/**
 * This class is the dialog fragment class for adding an ingredient to a recipe
 */
public class AddIngredient extends DialogFragment {

    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();
    private EditText description;
    private EditText quantity;
    private EditText unit;
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
        unit = view.findViewById(R.id.ingredientUnit);
        category = view.findViewById(R.id.ingredientCategory);
        categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        category.setAdapter(categoryAdapter);
        getCategories();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {
            Bundle selectedBundle = getArguments();
            Ingredient ingredientToEdit = (Ingredient) selectedBundle.get("selected_ingredient");
            initializeIngredient(ingredientToEdit);

            getCategories();
            return builder.setView(view).setTitle("Edit ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Edit", (dialogInterface, i) -> editClick(ingredientToEdit))
                    .create();
        }

        Ingredient create_ingredient = new Ingredient();
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ADD", null).create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (addClick(create_ingredient)) {
                    dialog.dismiss();
                }
            });
        });
        return dialog;
    }

    /**
     * This sets the information of the selected ingredient to the text fields
     * @param ingredient {@link Ingredient}
     *                                     the selected ingredient
     */
    public void initializeIngredient(Ingredient ingredient) {
        description.setText(ingredient.getDescription());
        quantity.setText(String.valueOf(ingredient.getAmount()));
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
     * Set the fields of an ingredient, returns true if the added ingredient is valid
     * and false otherwise
     * @param ingredient {@link Ingredient}
     *                                     the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean setFileds(Ingredient ingredient) {
        //Ingredient ingredient = new Ingredient();
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

        String addUnit = unit.getText().toString();
        ingredient.setUnit(addUnit);

        if (category.getSelectedItem() == null) {
            valid = false;
        } else {
            String addCategory = category.getSelectedItem().toString();
            ingredient.setCategory(addCategory);
        }

        return valid;
    }

    /**
     * Adds an ingredient to a recipe, returns true if the added ingredient is valid
     * and false otherwise
     * @param ingredient {@link Ingredient}
     *                                     the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean addClick(Ingredient ingredient) {
        boolean valid = setFileds(ingredient);
        if (valid) {
            listener.addRecipeIngredient(ingredient);
        }
        return valid;
    }

    /**
     * Edits an ingredient in a recipe, returns true if the added ingredient is valid
     * and false otherwise
     * @param ingredient {@link Ingredient}
     *                                     the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean editClick(Ingredient ingredient) {
        boolean valid = setFileds(ingredient);
        if (valid) {
            listener.editRecipeIngredient(ingredient);
        }
        return valid;
    }


    /**
     * A listener interface which provides callbacks to interact with events occuring in the dialog
     */
    public interface smallIngredientListener {
        void addRecipeIngredient(Ingredient new_ingredient);
        void editRecipeIngredient(Ingredient edit_ingredient);
    }
}
