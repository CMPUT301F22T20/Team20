package com.example.foodtracker.ui.ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;


public class AddIngredientDialog extends DialogFragment {

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setNeutralButton("ok", (dialogInterface, i) -> {
                    Ingredient egg = new Ingredient("egg",1.0,"Pantry","Food",5,"05/02/2022");
                    listener.onIngredientAdd(egg);
                }).create();

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
