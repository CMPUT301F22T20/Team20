package com.example.foodtracker.ui.recipes;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.ui.ingredients.IngredientDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class RecipeDialog extends DialogFragment {

    private EditText titleField;
    private EditText timeField;
    private EditText servingsField;
    private EditText categoryField;
    private ListView ingredientsListField;
    private EditText commentsField;
    private Button addIngredientButton;

    private ArrayAdapter<Ingredient> arrayAdapter;
    private ArrayList<Ingredient> arrayList;

    private RecipeDialog.RecipeDialogListener listener;

    /**
     * This function is called when the dialog fragment is attached to the current context.
     *
     * @param context This is the context which is of type {@link Context}
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (RecipeDialog.RecipeDialogListener) context;
        } catch (ClassCastException classCastException) {
            throw new RuntimeException("Must implement " + RecipeDialog.RecipeDialogListener.class.getSimpleName());
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
        View view = getLayoutInflater().inflate(R.layout.add_recipe_dialog, null);

        titleField = view.findViewById(R.id.recipeTitle);
        timeField = view.findViewById(R.id.recipePrepTime);
        servingsField = view.findViewById(R.id.recipeServings);
        categoryField = view.findViewById(R.id.recipeCategory);
        ingredientsListField = view.findViewById(R.id.ingredients);
        commentsField = view.findViewById(R.id.recipeComments);
        addIngredientButton = view.findViewById(R.id.addIngredient);

        arrayList = new ArrayList<>();
        //arrayAdapter = new CustomList(this, arrayList);

        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSmallerIngredientDialog();
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add a recipe")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addClick();
                    }
                }).create();

    }

    public void openSmallerIngredientDialog() {
        new AddIngredient().show(getParentFragmentManager(), "Add_ingredient");
    }


    public void addClick() {

        String title = titleField.getText().toString();
        String time = timeField.getText().toString();
        int time_min = 0;

        try {
            time_min = Integer.parseInt(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String servings = servingsField.getText().toString();
        int servings_int = 0;

        try {
            servings_int = Integer.parseInt(servings);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String category = categoryField.getText().toString();
        String comments = commentsField.getText().toString();

        Recipe add_recipe = new Recipe();
        add_recipe.setTitle(title);
        add_recipe.setPrepTime(time_min);
        add_recipe.setServings(servings_int);
        add_recipe.setCategory(category);
        add_recipe.setComment(comments);

        listener.onRecipeAdd(add_recipe);
    }




    public interface RecipeDialogListener {
        /**
         * Callback when an ingredient is added within the dialog
         */
        void onRecipeAdd(Recipe newRecipe);

        //void onRecipeEdit(Recipe oldRecipe);

    }
}


