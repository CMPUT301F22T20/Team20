package com.example.foodtracker.ui.ingredients.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.utils.Collection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class IngredientDialog extends DialogFragment {

    private EditText description;
    private EditText cost;
    private EditText quantity;
    private DatePicker expiry;
    private Ingredient ingredientToEdit;

    private Spinner location;
    private ArrayAdapter<String> locationAdapter;
    private final Collection<Location> locationCollection = new Collection<>(Location.class, new Location());
    private final List<String> locations = new ArrayList<>();


    private Spinner category;
    private ArrayAdapter<String> categoryAdapter;
    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();


    private IngredientDialogListener listener;

    /**
     * This function is called when the dialog fragment is attached to the current context.
     *
     * @param context This is the context which is of type {@link Context}
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (IngredientDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + IngredientDialogListener.class.getSimpleName());
        }
    }

    /**
     * Retrieves locations and categories from firestore and populates a string array with the content
     */
    private void getLocationsAndCategories(@Nullable Ingredient ingredient) {
        categoryCollection.getAll(list -> {
            for (Category category : list) {
                categories.add(category.getName());
                categoryAdapter.notifyDataSetChanged();
            }
            if (ingredient != null) {
                category.setSelection(categoryAdapter.getPosition(ingredient.getCategory()));
            }
        });
        locationCollection.getAll(list -> {
            for (Location location : list) {
                locations.add(location.getName());
                locationAdapter.notifyDataSetChanged();
            }
            if (ingredient != null) {
                location.setSelection(locationAdapter.getPosition(ingredient.getLocation()));
            }
        });
    }

    /**
     * This function is called when the dialog fragment is created
     *
     * @param savedInstanceState This is of type {@link Bundle}
     * @return This is of type {@link AlertDialog.Builder}
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.add_ingredient_dialog, null);
        description = view.findViewById(R.id.ingredientDescription);
        cost = view.findViewById(R.id.ingredientCost);
        quantity = view.findViewById(R.id.ingredientQuantity);
        location = view.findViewById(R.id.ingredientLocation);
        locationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, locations);
        location.setAdapter(locationAdapter);
        category = view.findViewById(R.id.ingredientCategory);
        categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, categories);
        category.setAdapter(categoryAdapter);
        expiry = view.findViewById(R.id.datePicker);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {
            Bundle selectedBundle = getArguments();
            ingredientToEdit = (Ingredient) selectedBundle.get("ingredient");
            initializeIngredient(ingredientToEdit);
            getLocationsAndCategories(ingredientToEdit);
            return builder.setView(view).setTitle("Edit ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Edit", (dialogInterface, i) -> editClick(ingredientToEdit))
                    .create();
        }
        getLocationsAndCategories(null);
        return builder.setView(view).setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialogInterface, i) -> addClick())
                .create();
    }

    public void initializeIngredient(Ingredient ingredient) {
        description.setText(ingredient.getDescription());
        cost.setText(String.valueOf(ingredient.getCost()));
        quantity.setText(String.valueOf(ingredient.getAmount()));
        setDatePicker(ingredient);
    }

    public void editClick(Ingredient ingredient) {
        setIngredientFields(ingredient);
        listener.onIngredientEdit(ingredient);
    }

    public void addClick() {
        Ingredient ingredient = new Ingredient();
        setIngredientFields(ingredient);
        listener.onIngredientAdd(ingredient);
    }

    private void setIngredientFields(Ingredient ingredient) {
        ingredient.setDescription(description.getText().toString());
        String costString = cost.getText().toString();
        double cost = 0.0;
        try {
            cost = Double.parseDouble(costString);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        ingredient.setCost(cost);
        String quantityString = quantity.getText().toString();
        int quantity = 1;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ingredient.setAmount(quantity);
        ingredient.setLocation(location.getSelectedItem().toString());
        ingredient.setCategory(category.getSelectedItem().toString());
        ingredient.setExpiry(String.format(Locale.CANADA, "%02d-%02d-%d", expiry.getDayOfMonth(), expiry.getMonth() + 1, expiry.getYear()));
    }

    private void setDatePicker(Ingredient ingredient) {
        String expiry = ingredient.getExpiry();
        Date expiryDate;
        try {
            expiryDate = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA).parse(expiry);
        } catch (ParseException exception) {
            expiryDate = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(expiryDate));
        this.expiry.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }


    public interface IngredientDialogListener {
        /**
         * Callback when an ingredient is added within the dialog
         */
        void onIngredientAdd(Ingredient newIngredient);

        void onIngredientEdit(Ingredient oldIngredient);

    }
}
