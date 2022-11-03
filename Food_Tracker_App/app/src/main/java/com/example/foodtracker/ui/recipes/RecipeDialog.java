package com.example.foodtracker.ui.recipes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.Recipe;

import java.util.ArrayList;

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

    private ImageView recipeImage;
    private Uri imageURI;
    private final ActivityResultLauncher<String> imageGalleryResultHandler = registerForActivityResult(new ActivityResultContracts.GetContent(),
            uri -> {
                recipeImage.setImageURI(uri);
                imageURI = uri;
            });

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
        ImageButton addRecipeImageButton = view.findViewById(R.id.recipe_image_button);
        recipeImage = view.findViewById(R.id.recipe_image);

        arrayList = new ArrayList<>();
        //arrayAdapter = new CustomList(this, arrayList);

        addIngredientButton.setOnClickListener(v -> openSmallerIngredientDialog());
        addRecipeImageButton.setOnClickListener(v -> addImageFromGallery());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a recipe")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("ADD", (dialogInterface, i) -> addClick()).create();
    }

    private void addImageFromGallery() {
        imageGalleryResultHandler.launch("image/*");
    }

    private void openSmallerIngredientDialog() {
        new AddIngredient().show(getParentFragmentManager(), "Add_ingredient");
    }

    /**
     * Populates the dialog fields from a {@link Recipe} instance
     * @param recipe to initialize form with
     */
    public void initializeRecipe(Recipe recipe) {
        if (!recipe.getImage().isEmpty()) {
            imageURI = Uri.parse(recipe.getImage());
            recipeImage.setImageURI(imageURI);
        }
    }


    private void addClick() {
        String title = titleField.getText().toString();
        String time = timeField.getText().toString();
        int timeMin = 0;
        try {
            timeMin = Integer.parseInt(time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String servings = servingsField.getText().toString();
        int servingsInt = 0;
        try {
            servingsInt = Integer.parseInt(servings);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String category = categoryField.getText().toString();
        String comments = commentsField.getText().toString();
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setPrepTime(timeMin);
        recipe.setServings(servingsInt);
        recipe.setCategory(category);
        recipe.setComment(comments);
        recipe.setImage(imageURI.toString());
        listener.onRecipeAdd(recipe);
    }


    public interface RecipeDialogListener {
        /**
         * Callback when an ingredient is added within the dialog
         */
        void onRecipeAdd(Recipe newRecipe);

        //void onRecipeEdit(Recipe oldRecipe);

    }
}


