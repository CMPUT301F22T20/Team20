package com.example.foodtracker.ui.ingredients.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.utils.Collection;

public class AddCategoryDialog extends DialogFragment {

    private EditText listItem;
    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final DialogInterface.OnDismissListener dismissListener;

    AddCategoryDialog(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.singleton_list_add, null);
        listItem = view.findViewById(R.id.singleton_list_add);
        listItem.setHint("Add a category");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog = builder.setView(view)
                .setTitle("New Location")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", null).create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                Category category = new Category(listItem.getText().toString());
                categoryCollection.exists(category, result -> {
                    if (Boolean.FALSE.equals(result)) {
                        categoryCollection.createDocument(category, this::dismiss);
                    } else {
                        listItem.setError("Location already exists");
                    }
                });
            });
        });
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }
}
