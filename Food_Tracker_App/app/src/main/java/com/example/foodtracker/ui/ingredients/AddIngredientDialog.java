package com.example.foodtracker.ui.ingredients;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddIngredientDialog extends DialogFragment {

    private EditText description;
    private EditText cost;
    private EditText quantity;
    private Spinner location;
    private Spinner category;
    private DatePicker expiry;

    private AddIngredientDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddIngredientDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + AddIngredientDialogListener.class.getSimpleName());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.add_ingredient_dialog, null);

        description = view.findViewById(R.id.ingredientDescription);
        cost = view.findViewById(R.id.ingredientCost);
        quantity = view.findViewById(R.id.ingredientQuantity);

        location = (Spinner) view.findViewById(R.id.ingredientLocation);
        location.setAdapter(createAdapter(R.array.locationList));


        category = (Spinner)view.findViewById(R.id.ingredientCategory);
        category.setAdapter(createAdapter(R.array.categoryList));


        expiry = view.findViewById(R.id.datePicker);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add an ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        positiveClick();
                    }
                }).create();

    }




    /**
     * This method creates an ArrayAdapter for the spinner
     * @param spinnerDataXML string-array in xml
     * @return adapter of type ArrayAdapter<CharSequence>
     * copyright: Incimo
     *  link: https://blog.csdn.net/qq_41912398/article/details/105548856
     */
    public ArrayAdapter<CharSequence> createAdapter(int spinnerDataXML) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                spinnerDataXML, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public void positiveClick() {
        //description of the added ingredient
        String add_description = description.getText().toString();

        //cost of the added ingredient
        String add_cost_str = cost.getText().toString();
        Double add_cost_double = 0.0;
        try{
            add_cost_double = Double.parseDouble(add_cost_str);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
            add_cost_double = 0.0;
        }

        //quantity of the added ingredient
        String add_quantity_str = quantity.getText().toString();
        int add_quantity_int = 1;
        try{
            add_quantity_int = Integer.parseInt(add_quantity_str);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            add_quantity_int = 1;
        }

        //location of the added ingredient
        String add_location = location.getSelectedItem().toString();

        //category of the added ingredient
        String add_category = category.getSelectedItem().toString();

        //expiry date of the added ingredient
        String add_date = expiry.getDayOfMonth()+"/"+ (expiry.getMonth() + 1)+"/"+expiry.getYear();

        Ingredient add_ingredient = new Ingredient(add_description,
                add_cost_double, add_location, add_category,
                add_quantity_int, add_date);

        listener.onIngredientAdd(add_ingredient);
    }

    public interface AddIngredientDialogListener {

        /**
         * Callback when an ingredient is added within the dialog
         */
        void onIngredientAdd(Ingredient newIngredient);

        /**
         * Callback when the cancel button is clicked within the dialog
         */
        void onCancel();
    }
}