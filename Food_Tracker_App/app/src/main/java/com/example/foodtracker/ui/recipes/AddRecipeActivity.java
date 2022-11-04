package com.example.foodtracker.ui.recipes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.model.ingredient.Ingredient;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity implements AddIngredient.smallIngredientListener {

    public static final String RECIPE_KEY = "recipe";
    private EditText titleField;
    private EditText timeField;
    private EditText servingsField;
    private EditText categoryField;
    private EditText commentsField;

    private ImageView recipeImage;
    private Uri imageURI;
    private final ActivityResultLauncher<String> imageGalleryResultHandler = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        recipeImage.setImageURI(uri);
        imageURI = uri;
    });

    private ArrayAdapter<Ingredient> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_recipe);

        titleField = findViewById(R.id.recipeTitle);
        timeField = findViewById(R.id.recipePrepTime);
        servingsField = findViewById(R.id.recipeServings);
        categoryField = findViewById(R.id.recipeCategory);
        commentsField = findViewById(R.id.recipeComments);
        Button addIngredientButton = findViewById(R.id.addIngredient);
        Button confirmButton = findViewById(R.id.recipes_confirm);
        Button cancelButton = findViewById(R.id.recipes_cancel);

        ListView ingredientsListField = findViewById(R.id.ingredients);
        ArrayList<Ingredient> arrayList = new ArrayList<>();
        adapter = new CustomList(this, arrayList);
        ingredientsListField.setAdapter(adapter);
        addIngredientButton.setOnClickListener(view -> new AddIngredient().show(getSupportFragmentManager(), "Add_ingredient"));

        ImageButton addRecipeImageButton = findViewById(R.id.recipe_image_button);
        recipeImage = findViewById(R.id.recipe_image);
        addRecipeImageButton.setOnClickListener(v -> addImageFromGallery());

        confirmButton.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra(RECIPE_KEY, createRecipe());
            setResult(RESULT_OK, intent);
            finish();
        });

        cancelButton.setOnClickListener(view -> finish());
    }

    @Override
    public void addRecipeIngredient(Ingredient new_ingredient) {
        adapter.add(new_ingredient);
    }

    /**
     * Populates the dialog fields from a {@link Recipe} instance
     *
     * @param recipe to initialize form with
     */
    public void initializeRecipe(Recipe recipe) {
        if (!recipe.getImage().isEmpty()) {
            imageURI = Uri.parse(recipe.getImage());
            recipeImage.setImageURI(imageURI);
        }
    }

    private void addImageFromGallery() {
        imageGalleryResultHandler.launch("image/*");
    }

    private Recipe createRecipe() {
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
        if (imageURI != null) {
            recipe.setImage(imageURI.toString());
        }
        return recipe;
    }
}