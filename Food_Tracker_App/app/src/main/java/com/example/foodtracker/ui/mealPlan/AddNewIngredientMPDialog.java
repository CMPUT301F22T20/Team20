package com.example.foodtracker.ui.mealPlan;

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
import com.example.foodtracker.model.IngredientUnit.IngredientUnit;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.model.recipe.SimpleIngredient;
import com.example.foodtracker.ui.recipes.AddSimpleIngredient;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.List;

public class AddNewIngredientMPDialog extends DialogFragment {

    private Spinner category;
    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;

    private Spinner unit;
    private ArrayAdapter<String> unitAdapter;
    private final List<String> ingredientUnits = new ArrayList<>();

    private EditText description;
    private EditText quantity;

    private AddNewIngredientMPDialog.smallIngredientListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddNewIngredientMPDialog.smallIngredientListener) {
            listener = (AddNewIngredientMPDialog.smallIngredientListener) context;
        } else {
            throw new RuntimeException(context + " must implement smallIngredientListener");
        }
    }

    /**
     * Retrieves locations and categories from firestore and populates a string array with the content,
     * initializes the unit dropdown from {@link com.example.foodtracker.model.IngredientUnit.IngredientUnit} values
     */
    private void initializeDropdowns(@Nullable Ingredient ingredient) {
        categoryCollection.getAll(list -> {
            for (Category category : list) {
                categories.add(category.getName().toUpperCase());
                categoryAdapter.notifyDataSetChanged();
            }
        });

        for (IngredientUnit ingredientUnit : IngredientUnit.values()) {
            ingredientUnits.add(ingredientUnit.name());
        }
        unitAdapter.notifyDataSetChanged();
        if (ingredient != null) {
            unit.setSelection(unitAdapter.getPosition(ingredient.getUnit()));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.smaller_add_ingredient_dialog, null);
        description = view.findViewById(R.id.ingredientDescription);
        quantity = view.findViewById(R.id.ingredientQuantity);

        unit = view.findViewById(R.id.ingredientUnit);
        unitAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ingredientUnits);
        unit.setAdapter(unitAdapter);

        category = view.findViewById(R.id.ingredientCategory);
        categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        category.setAdapter(categoryAdapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        initializeDropdowns(null);
        return createAddIngredientDialog(view, builder);
    }


    private AlertDialog createAddIngredientDialog(View view, AlertDialog.Builder builder) {

        Ingredient create_ingredient = new Ingredient();
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null).create();
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
     * Set the fields of an ingredient, returns true if the added ingredient is valid
     * and false otherwise
     * the ingredient to be added to a recipe
     *
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean setFields(Ingredient ingredient) {
        boolean valid = true;

        String addDescription = description.getText().toString();
        ingredient.setDescription(addDescription);
        if (addDescription.isEmpty()) {
            description.setError("Description must not be empty");
            valid = false;
        }

        String addQuantityStr = quantity.getText().toString();
        try {
            double addQuantityDouble = Double.parseDouble(addQuantityStr);
            ingredient.setAmount(addQuantityDouble);
        } catch (NumberFormatException e) {
            quantity.setError("Invalid amount");
            valid = false;
        }


        if (unit.getSelectedItem() == null) {
            valid = false;
        } else {
            String addUnit = unit.getSelectedItem().toString();
            ingredient.setUnit(addUnit);
        }


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
     *
     * @param ingredient {@link Ingredient} the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean addClick(Ingredient ingredient) {
        boolean valid = setFields(ingredient);
        if (valid) {
            listener.addRecipeIngredient(ingredient);
        }
        return valid;
    }


    /**
     * A listener interface which provides callbacks to interact with events occuring in the dialog
     */
    public interface smallIngredientListener {
        void addRecipeIngredient(Ingredient new_ingredient);
    }
}


