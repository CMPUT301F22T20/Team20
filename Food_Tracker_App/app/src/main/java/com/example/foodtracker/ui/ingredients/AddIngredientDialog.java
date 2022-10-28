package com.example.foodtracker.ui.ingredients;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;

import java.util.Calendar;


public class AddIngredientDialog extends DialogFragment {

    private EditText description;
    private EditText cost;
    private EditText quantity;
    private Spinner location;
    private ArrayAdapter<CharSequence> locationAdapter;
    private Spinner category;
    private ArrayAdapter<CharSequence> categoryAdapter;
    private DatePicker expiry;
    private EditText dateText;

<<<<<<< HEAD:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/IngredientDialog.java
    private Ingredient edit_ingredient;
    private IngredientDialogListener listener;
    /**
     * This is the listener for the add ingredient dialogue
     */
=======
    private AddIngredientDialogListener listener;
>>>>>>> 4be9dd76a139da10011255ba1957774a30bfb990:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/AddIngredientDialog.java

    /**
     * This function is called when the dialog fragment is attached to the current context.
     *
     * @param context This is the context which is of type {@link Context}
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddIngredientDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + AddIngredientDialogListener.class.getSimpleName());
        }
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

        location = (Spinner) view.findViewById(R.id.ingredientLocation);
        locationAdapter = createAdapter(R.array.locationList);
        location.setAdapter(locationAdapter);


        category = (Spinner) view.findViewById(R.id.ingredientCategory);
        categoryAdapter = createAdapter(R.array.categoryList);
        category.setAdapter(categoryAdapter);


        expiry = view.findViewById(R.id.datePicker);
        dateText = view.findViewById(R.id.showDate);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if (getArguments() != null) {
            Bundle selected_bundle = getArguments();
            edit_ingredient = (Ingredient) selected_bundle.get("ingredient");

            setIngredient(edit_ingredient);

            return builder
                    .setView(view)
                    .setTitle("Add/Edit ingredient")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            editClick(edit_ingredient);
                        }
                    }).create();
        }
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

<<<<<<< HEAD:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/IngredientDialog.java
=======



>>>>>>> 4be9dd76a139da10011255ba1957774a30bfb990:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/AddIngredientDialog.java
    /**
     * This method creates an ArrayAdapter for the spinner
     *
     * @param spinnerDataXML string-array in xml
     * @return adapter of type ArrayAdapter<CharSequence>
     * copyright: Incimo
     * link: https://blog.csdn.net/qq_41912398/article/details/105548856
     */
    public ArrayAdapter<CharSequence> createAdapter(int spinnerDataXML) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                spinnerDataXML, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public void setIngredient(Ingredient old_ingredient) {

        //set description
        description.setText(old_ingredient.getDescription());
        //set cost
        cost.setText(String.valueOf(old_ingredient.getCost()));
        //set amount
        quantity.setText(String.valueOf(old_ingredient.getAmount()));
        //set location
        int locationIndex = locationAdapter.getPosition(old_ingredient.getLocation());
        location.setSelection(locationIndex);
        //set category
        int categoryIndex = categoryAdapter.getPosition(old_ingredient.getCategory());
        category.setSelection(categoryIndex);

        dateText.setText(old_ingredient.getExpiry());
        //set expiry date
        String old_date = old_ingredient.getExpiry();
        String old_day = old_date.substring(0, 2);
        String old_month = old_date.substring(3, 5);
        String old_year = old_date.substring(6);

        //current date
        Calendar calendar = Calendar.getInstance();
        int year_int = calendar.get(Calendar.YEAR);
        int month_int = calendar.get(Calendar.MONTH);
        int day_int = calendar.get(Calendar.DAY_OF_MONTH);

        try {
            year_int = Integer.parseInt(old_year);
        } catch (NumberFormatException e) {
            Log.d(TAG, "setIngredient: " + year_int);
            e.printStackTrace();
        }

        try {
            month_int = Integer.parseInt(old_month);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            day_int = Integer.parseInt(old_day);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //set expiry date
        expiry.updateDate(year_int, month_int - 1, day_int);


    }

    public void editClick(Ingredient old_ingredient) {
        //edit description of the ingredient
        String edit_description = description.getText().toString();
        old_ingredient.setDescription(edit_description);

        //edit cost of the ingredient
        String edit_cost_str = cost.getText().toString();
        Double edit_cost_double = 0.0;
        try {
            edit_cost_double = Double.parseDouble(edit_cost_str);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        old_ingredient.setCost(edit_cost_double);

        //edit quantity of the ingredient
        String edit_quantity_str = quantity.getText().toString();
        int edit_quantity_int = 1;
        try {
            edit_quantity_int = Integer.parseInt(edit_quantity_str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        old_ingredient.setAmount(edit_quantity_int);

        //update location of the ingredient
        String edit_location = location.getSelectedItem().toString();
        old_ingredient.setLocation(edit_location);

        //update category of the ingredient
        String edit_category = category.getSelectedItem().toString();
        old_ingredient.setCategory(edit_category);

        //edit expiry date of the ingredient
        String edit_date = expiry.getDayOfMonth() + "/" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        if ((expiry.getDayOfMonth() < 10) && (expiry.getMonth() >= 9)) {
            edit_date = "0" + expiry.getDayOfMonth() + "/" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        } else if ((expiry.getDayOfMonth() >= 10) && (expiry.getMonth() < 9)) {
            edit_date = expiry.getDayOfMonth() + "/0" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        } else if ((expiry.getDayOfMonth() < 10) && (expiry.getMonth() < 9)) {
            edit_date = "0" + expiry.getDayOfMonth() + "/0" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        }
        old_ingredient.setExpiry(edit_date);


        listener.onIngredientEdit(old_ingredient);
    }

    public void addClick() {
        //description of the added ingredient
        String add_description = description.getText().toString();

        //cost of the added ingredient
        String add_cost_str = cost.getText().toString();
        Double add_cost_double = 0.0;
        try {
            add_cost_double = Double.parseDouble(add_cost_str);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            add_cost_double = 0.0;
        }

        //quantity of the added ingredient
        String add_quantity_str = quantity.getText().toString();
        int add_quantity_int = 1;
        try {
            add_quantity_int = Integer.parseInt(add_quantity_str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            add_quantity_int = 1;
        }

        //location of the added ingredient
        String add_location = location.getSelectedItem().toString();

        //category of the added ingredient
        String add_category = category.getSelectedItem().toString();

        //expiry date of the added ingredient
        String add_date = expiry.getDayOfMonth() + "/" + (expiry.getMonth() + 1) + "/" + expiry.getYear();

        if ((expiry.getDayOfMonth() < 10) && (expiry.getMonth() >= 9)) {
            add_date = "0" + expiry.getDayOfMonth() + "/" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        } else if ((expiry.getDayOfMonth() >= 10) && (expiry.getMonth() < 9)) {
            add_date = expiry.getDayOfMonth() + "/0" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        } else if ((expiry.getDayOfMonth() < 10) && (expiry.getMonth() < 9)) {
            add_date = "0" + expiry.getDayOfMonth() + "/0" + (expiry.getMonth() + 1) + "/" + expiry.getYear();
        }

        Ingredient add_ingredient = new Ingredient(add_description,
                add_cost_double, add_location, add_category,
                add_quantity_int, add_date);

        listener.onIngredientAdd(add_ingredient);
    }

<<<<<<< HEAD:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/IngredientDialog.java
    public interface IngredientDialogListener {
=======
    public interface AddIngredientDialogListener {
>>>>>>> 4be9dd76a139da10011255ba1957774a30bfb990:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/AddIngredientDialog.java

        /**
         * Callback when an ingredient is added within the dialog
         */
        void onIngredientAdd(Ingredient newIngredient);

<<<<<<< HEAD:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/IngredientDialog.java
        void onIngredientEdit(Ingredient oldIngredient);


=======
        /**
         * Callback when the cancel button is clicked within the dialog
         */
        void onCancel();
>>>>>>> 4be9dd76a139da10011255ba1957774a30bfb990:Food_Tracker_App/app/src/main/java/com/example/foodtracker/ui/ingredients/AddIngredientDialog.java
    }
}
