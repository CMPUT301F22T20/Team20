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
import com.example.foodtracker.model.IngredientUnit.IngredientUnit;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.recipe.SimpleIngredient;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the dialog fragment class for adding an ingredient to a recipe
 */
public class RecipeIngredientDialog extends DialogFragment {

    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();
    private final List<String> ingredientUnits = new ArrayList<>();
    private EditText description;
    private EditText quantity;
    private Spinner category;
    private ArrayAdapter<String> categoryAdapter;
    private Spinner unit;
    private ArrayAdapter<String> unitAdapter;
    private recipeIngredientDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof recipeIngredientDialogListener) {
            listener = (recipeIngredientDialogListener) context;
        } else {
            throw new RuntimeException("Must implement " + recipeIngredientDialogListener.class.getSimpleName());
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
        Bundle arguments = getArguments();
        if (arguments != null) {
            return createEditRecipeIngredientDialog(view, builder, arguments);
        }
        return createAddRecipeIngredientDialog(view, builder);
    }

    /**
     * This sets the information of the selected ingredient to the text fields
     *
     * @param ingredient {@link SimpleIngredient} the selected ingredient to be edited
     */
    public void initializeIngredient(SimpleIngredient ingredient) {
        description.setText(ingredient.getDescription());
        quantity.setText(String.valueOf(ingredient.getAmount()));
    }

    private AlertDialog createAddRecipeIngredientDialog(View view, AlertDialog.Builder builder) {
        initializeDropdowns(null);
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null).create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            SimpleIngredient ingredient = new SimpleIngredient();
            button.setOnClickListener(v -> {
                if (addClick(ingredient)) {
                    dialog.dismiss();
                }
            });
        });
        return dialog;
    }

    private AlertDialog createEditRecipeIngredientDialog(View view, AlertDialog.Builder builder, Bundle arguments) {
        SimpleIngredient ingredientToEdit = (SimpleIngredient) arguments.get("selected_ingredient");
        initializeIngredient(ingredientToEdit);
        initializeDropdowns(ingredientToEdit);
        AlertDialog dialog = builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null).create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                if (editClick(ingredientToEdit)) {
                    dialog.dismiss();
                }
            });
        });
        return dialog;
    }

    /**
     * Retrieves categories from firestore and populates a string array with the content,
     * initializes the unit dropdown from {@link com.example.foodtracker.model.IngredientUnit.IngredientUnit} values
     */
    private void initializeDropdowns(@Nullable SimpleIngredient ingredient) {
        categoryCollection.getAll(list -> {
            for (Category category : list) {
                categories.add(category.getName().toUpperCase());
                categoryAdapter.notifyDataSetChanged();
            }
            if (ingredient != null) {
                category.setSelection(categoryAdapter.getPosition(ingredient.getCategory()));
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

    /**
     * Set the fields of an ingredient, returns true if the added ingredient is valid
     * and false otherwise
     *
     * @param ingredient {@link SimpleIngredient} the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean setFields(SimpleIngredient ingredient) {
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

        if (unit.getSelectedItem() == null) {
            valid = false;
        } else {
            ingredient.setUnit(unit.getSelectedItem().toString());
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
     * @param ingredient {@link SimpleIngredient} the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean addClick(SimpleIngredient ingredient) {
        boolean valid = setFields(ingredient);
        if (valid) {
            listener.addRecipeIngredient(ingredient);
        }
        return valid;
    }

    /**
     * Edits an ingredient in a recipe, returns true if the added ingredient is valid
     * and false otherwise
     *
     * @param ingredient {@link SimpleIngredient} the ingredient to be added to a recipe
     * @return true if the added ingredient is valid, false otherwise
     */
    private boolean editClick(SimpleIngredient ingredient) {
        boolean valid = setFields(ingredient);
        if (valid) {
            listener.editRecipeIngredient(ingredient);
        }
        return valid;
    }


    /**
     * A listener interface which provides callbacks to interact with events occurring in the dialog
     */
    public interface recipeIngredientDialogListener {
        void addRecipeIngredient(SimpleIngredient ingredient);

        void editRecipeIngredient(SimpleIngredient ingredient);
    }
}