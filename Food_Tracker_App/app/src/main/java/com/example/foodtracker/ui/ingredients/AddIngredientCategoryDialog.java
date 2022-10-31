package com.example.foodtracker.ui.ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddIngredientCategoryDialog extends DialogFragment {

    private EditText category;
    private OnCategoryFragmentInteractionListener listener;

    public interface OnCategoryFragmentInteractionListener {
        public void onAddCategoryCreates(String newCategory);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCategoryFragmentInteractionListener){
            listener = (OnCategoryFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "interface method required");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.add_ingredient_category_dialog, null);

        category = view.findViewById(R.id.ingredientCategoryDescription);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("New Ingredient Category")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputCategory = category.getText().toString();
                        listener.onAddCategoryCreates(inputCategory);
                    }
                })
                .create();
    }
}
