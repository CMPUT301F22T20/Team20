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

public class AddLocationDialog extends DialogFragment {

    private EditText location;
    private OnLocationFragmentInteractionListener listener;

    public interface OnLocationFragmentInteractionListener {
        public void onAddLocationCreates(String newLocation);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLocationFragmentInteractionListener){
            listener = (OnLocationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + "interface method required");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.add_location_dialog, null);

        location = view.findViewById(R.id.ingredientLocationDescription);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("New Location")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String inputLocation = location.getText().toString();
                        listener.onAddLocationCreates(inputLocation);
                    }
                })
                .create();
    }
}
