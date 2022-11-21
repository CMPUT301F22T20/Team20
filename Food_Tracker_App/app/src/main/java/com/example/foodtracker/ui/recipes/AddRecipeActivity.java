package com.example.foodtracker.ui.recipes;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.recipe.Category;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddRecipeActivity extends AppCompatActivity implements AddIngredient.smallIngredientListener {

    public static final String RECIPE_KEY = "recipe";

    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private Spinner categoryField;

    private EditText titleField;
    private EditText timeField;
    private EditText servingsField;
    private EditText commentsField;
    private ImageView recipeImage;
    private Uri imageURI;
    private  ListView ingredientsListField;
    private final ActivityResultLauncher<String> imageGalleryResultHandler = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        recipeImage.setImageURI(uri);
        imageURI = uri;
    });

    private ArrayAdapter<Ingredient> adapter;
    private ArrayList<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_recipe);

        titleField = findViewById(R.id.recipeTitle);
        timeField = findViewById(R.id.recipePrepTime);
        servingsField = findViewById(R.id.recipeServings);
        categoryField = findViewById(R.id.recipeCategory);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryField.setAdapter(categoryAdapter);
        getCategories(null);
        commentsField = findViewById(R.id.recipeComments);
        Button addIngredientButton = findViewById(R.id.addIngredient);
        Button confirmButton = findViewById(R.id.recipes_confirm);
        Button cancelButton = findViewById(R.id.recipes_cancel);

        ingredientsListField = findViewById(R.id.ingredients);
        ingredientsListField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingredient selected_ingredient = (Ingredient) ingredientsListField.getItemAtPosition(i);
                Bundle args = new Bundle();
                args.putSerializable("selected_ingredient", selected_ingredient);
                AddIngredient editFrag = new AddIngredient();
                editFrag.setArguments(args);
                editFrag.show(getSupportFragmentManager(), "EDIT_INGREDIENT_IN_RECIPE");
            }
        });

//        ingredientList = new ArrayList<>();
//        adapter = new CustomList(this, ingredientList);
//        ingredientsListField.setAdapter(adapter);
        addIngredientButton.setOnClickListener(view -> new AddIngredient().show(getSupportFragmentManager(), "Add_ingredient"));

        ImageButton addRecipeImageButton = findViewById(R.id.recipe_image_button);
        recipeImage = findViewById(R.id.recipe_image);
        addRecipeImageButton.setOnClickListener(v -> addImageFromGallery());

        /*
        Edit a recipe
         */
        if (getIntent().getExtras() != null) {
            //Receive the Recipe object from RecipesMainScreen
            Intent intent1 = getIntent();
            Recipe edit_recipe = (Recipe) intent1.getSerializableExtra("EDIT_RECIPE");
            getCategories(edit_recipe);
            initializeEditRecipe(edit_recipe);

            confirmButton.setOnClickListener(view -> {
                Intent intent = new Intent();
                Boolean valid = setRecipeFields(edit_recipe);

                if (valid) {
                    intent.putExtra("EDIT_RECIPE", edit_recipe);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            });
        }

        /*
        Add a recipe
         */
        else {
            ingredientList = new ArrayList<>();
            adapter = new CustomList(this, ingredientList);
            ingredientsListField.setAdapter(adapter);

            confirmButton.setOnClickListener(view -> {
                Intent intent = new Intent();
                Recipe recipe = new Recipe();
                getCategories(null);
                Boolean valid = setRecipeFields(recipe);
                if (valid) {
                    intent.putExtra(RECIPE_KEY, recipe);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            });
        }

        cancelButton.setOnClickListener(view -> finish());

    }

    @Override
    public void addRecipeIngredient(Ingredient new_ingredient) {
        adapter.add(new_ingredient);
    }

    @Override
    public void editRecipeIngredient(Ingredient edit_ingredient) {
        adapter.notifyDataSetChanged();
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

    /**
     * Populates the dialog fields from a {@link Recipe} instance
     *
     * @param recipe to initialize form with
     */
    public void initializeEditRecipe(Recipe recipe) {
        if (!recipe.getImage().isEmpty()) {
            imageURI = Uri.parse(recipe.getImage());
            recipeImage.setImageURI(imageURI);
        }

        titleField.setText(recipe.getTitle());
        timeField.setText(String.valueOf(recipe.getPrepTime()));
        servingsField.setText(String.valueOf(recipe.getServings()));
        commentsField.setText(recipe.getComment());

        ingredientList = recipe.getIngredients();
        adapter = new CustomList(this, ingredientList);
        ingredientsListField.setAdapter(adapter);
    }

    /**
     * Retrieves categories from firestore and populates a string array with the content
     */
    private void getCategories(@Nullable Recipe recipe) {
        categoryCollection.getAll(list -> {
            for (com.example.foodtracker.model.recipe.Category category : list) {
                categories.add(category.getName());
                categoryAdapter.notifyDataSetChanged();
            }
            if (recipe != null) {
                categoryField.setSelection(categoryAdapter.getPosition(recipe.getCategory()));
            }
        });
    }

    private void addImageFromGallery() {
        imageGalleryResultHandler.launch("image/*");
    }

    /**
     * Setting the details of a recipe.
     * Return true if the title is not empty; otherwise, return false;
     * @return {@link Boolean} valid
     */
    private Boolean setRecipeFields(Recipe recipe) {

        Boolean valid = true;

        String title = titleField.getText().toString();
        recipe.setTitle(title);
        if (title.isEmpty()) {
            titleField.setError("Title must not be empty");
            valid = false;
        }

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
        String category = categoryField.getSelectedItem().toString();
        String comments = commentsField.getText().toString();

        recipe.setPrepTime(timeMin);
        recipe.setServings(servingsInt);
        recipe.setCategory(category);
        recipe.setComment(comments);
        recipe.setIngredients(ingredientList);
        if (imageURI != null) {
            recipe.setImage(imageURI.toString());
        }
        return valid;
    }
}