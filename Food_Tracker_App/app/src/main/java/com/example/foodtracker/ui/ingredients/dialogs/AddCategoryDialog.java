package com.example.foodtracker.ui.ingredients.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.utils.Collection;

/**
 * An object of this class represents the dialog used to add a new Ingredient Category
 * This class inherits from {@link DialogFragment}
 */
public class AddCategoryDialog extends DialogFragment {

    private EditText listItem;
    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.singleton_list_add, null);
        listItem = view.findViewById(R.id.singleton_list_add);
        listItem.setHint("Add a category");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("New Category")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, i) -> {
                    Category category = new Category(listItem.getText().toString());
                    categoryCollection.createDocument(category, this::dismiss);
                })
                .create();
    }
}
