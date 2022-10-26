package com.example.foodtracker.ui.ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;

public class AddIngredientDialog extends DialogFragment {
    private EditText ingredientDescription;
    private EditText ingredientAmount;
    private EditText ingredientCost;
    private EditText ingredientExpiry;
    private EditText ingredientLocation;
    private EditText ingredientCategory;

    private OnFragmentInteractionListener listener;

    //private Ingredient add_ingredient;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Ingredient newIngredient);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_ingredient_dialog, null);

        ingredientDescription = view.findViewById(R.id.ingredientDescription);
        ingredientAmount = view.findViewById(R.id.ingredientQuantity);
        ingredientCost = view.findViewById((R.id.ingredientCost));
        ingredientCategory = view.findViewById(R.id.ingredientCategory);
        ingredientLocation = view.findViewById(R.id.ingredientLocation);
        ingredientExpiry = view.findViewById(R.id.ingredientDate);

        //View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.ingredient_main,null);
        //RecyclerView ingredientList = view2.findViewById(R.id.ingredient_list);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        return builder
                .setView(view)
                .setTitle("Add an Ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String description = ingredientDescription.getText().toString();
                        /*
                        String amount = ingredientAmount.getText().toString();
                        String cost = ingredientCost.getText().toString();
                        String category = ingredientCategory.getText().toString();
                        String location = ingredientLocation.getText().toString();
                        String date = ingredientExpiry.getText().toString();
                        */
                        Ingredient new_ingredient = new Ingredient(description);
                        /*
                        int amount_int;
                        try {
                            amount_int = Integer.parseInt(amount);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            amount_int = 1;
                        }
                        new_ingredient.setAmount(amount_int);

                        Double cost_num;
                        try {
                            cost_num = Double.parseDouble(cost);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            cost_num = 0.0;
                        }
                        new_ingredient.setCost(cost_num);

                        new_ingredient.setCategory(category);
                        new_ingredient.setLocation(location);
                        new_ingredient.setExpiry(date);
                        */
                        listener.onOkPressed(new_ingredient);
                    }
                }).create();



    }
}
