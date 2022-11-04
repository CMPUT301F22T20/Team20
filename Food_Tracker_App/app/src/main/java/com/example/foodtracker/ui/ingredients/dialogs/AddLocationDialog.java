package com.example.foodtracker.ui.ingredients.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Document;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.utils.Collection;

public class AddLocationDialog extends DialogFragment {

    private EditText listItem;
    private final Collection<Location> locationCollection = new Collection<>(Location.class, new Location());

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.singleton_list_add, null);
        listItem = view.findViewById(R.id.singleton_list_add);
        listItem.setHint("Add a location");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("New Location")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, i) -> {
                    Location location = new Location(listItem.getText().toString());
                    locationCollection.createDocument(location, this::dismiss);
                })
                .create();
    }
}
