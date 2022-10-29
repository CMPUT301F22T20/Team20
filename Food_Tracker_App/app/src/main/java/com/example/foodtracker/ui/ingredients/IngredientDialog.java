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


public class IngredientDialog extends DialogFragment {

    private IngredientDialogListener listener;
    /**
     * This is the listener for the add ingredient dialogue
     */

    /**
     * This function is called when the dialog fragment is attached to the current context.
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
     * This function is called when the dialog fragment is created
     * @param savedInstanceState This is of type {@link Bundle}
     * @return This is of type {@link AlertDialog.Builder}
     */
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
    /**
     * This is an interface to specify functions that need to be implemented when a dialog fragment is created
     */
    public interface IngredientDialogListener {

        /**
         * Callback when an ingredient is added within the dialog
         */
        void onIngredientAdd(Ingredient newIngredient);
    }
}