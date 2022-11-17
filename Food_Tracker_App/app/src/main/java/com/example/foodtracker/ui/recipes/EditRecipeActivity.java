package com.example.foodtracker.ui.recipes;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.model.recipe.Category;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.ingredients.dialogs.IngredientDialog;
import com.example.foodtracker.utils.Collection;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.foodtracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is for the activity of editing a recipe
 */
public class EditRecipeActivity extends AppCompatActivity implements AddIngredient.smallIngredientListener{
    public static final String Edit_RECIPE_KEY = "EDIT_RECIPE";

    private final Collection<Category> categoryCollection = new Collection<>(Category.class, new Category());
    private final List<String> categories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private Spinner categoryField;

    private TextView titleField;
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

    private Recipe edit_recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        titleField = findViewById(R.id.recipeTitle);
        timeField = findViewById(R.id.recipePrepTime);
        servingsField = findViewById(R.id.recipeServings);
        categoryField = findViewById(R.id.recipeCategory);
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        categoryField.setAdapter(categoryAdapter);
        getCategories();
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
        addIngredientButton.setOnClickListener(view -> new AddIngredient().show(getSupportFragmentManager(), "Edit_ingredient"));

        ImageButton addRecipeImageButton = findViewById(R.id.recipe_image_button);
        recipeImage = findViewById(R.id.recipe_image);
        addRecipeImageButton.setOnClickListener(v -> addImageFromGallery());

        //Receive the Recipe object from RecipesMainScreen
        //Recipe edit_recipe;
        Intent intent1 = getIntent();
        edit_recipe = (Recipe) intent1.getSerializableExtra(Edit_RECIPE_KEY);

        initializeRecipe(edit_recipe);


        /**
         * Send the edited recipe
         */
        confirmButton.setOnClickListener(view -> {
            Recipe edited_r = createRecipe();
            Intent intent = new Intent(getApplicationContext(), RecipesMainScreen.class);
            intent.putExtra("EDIT_RECIPE", edited_r);
            setResult(RESULT_OK, intent);
            finish();

        });

        cancelButton.setOnClickListener(view -> finish());

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

        titleField.setText(recipe.getTitle());
        timeField.setText(String.valueOf(recipe.getPrepTime()));
        servingsField.setText(String.valueOf(recipe.getServings()));
        commentsField.setText(recipe.getComment());

        ArrayList<Ingredient> arrayList = recipe.getIngredients();
        adapter = new CustomList(this, arrayList);
        ingredientsListField.setAdapter(adapter);

    }

    /**
     * Retrieves categories from firestore and populates a string array with the content
     */
    private void getCategories() {
        categoryCollection.getAll(list -> {
            for (Category category : list) {
                categories.add(category.getName());
                categoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addImageFromGallery() {
        imageGalleryResultHandler.launch("image/*");
    }

    /**
     * Create a recipe with the edited details of a recipe
     *
     * @return revised_recipe {@link Recipe} a recipe with edited attributes
     *
     */
    private Recipe createRecipe() {
        //String title = titleField.getText().toString();
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

        //recipe.setTitle(title);
        Recipe revised_recipe = new Recipe();
        revised_recipe.setTitle(titleField.getText().toString());
        revised_recipe.setPrepTime(timeMin);
        revised_recipe.setServings(servingsInt);
        revised_recipe.setCategory(category);
        revised_recipe.setComment(comments);
        if (imageURI != null) {
            revised_recipe.setImage(imageURI.toString());
        }
        return revised_recipe;
    }

    @Override
    public void addRecipeIngredient(Ingredient new_ingredient) {
        adapter.add(new_ingredient);
    }

    @Override
    public void editRecipeIngredient(Ingredient edit_ingredient) {
        int index = 0;
        for (index = 0; index < edit_recipe.getIngredients().size(); index++) {
            if (Objects.equals(edit_recipe.getIngredients().get(index).getDescription(),
                    edit_ingredient.getDescription())) {
                break;
            }
        }
        int finalIndex = index;
        edit_recipe.getIngredients().get(finalIndex).setAmount(edit_ingredient.getAmount());
        edit_recipe.getIngredients().get(finalIndex).setCategory(edit_ingredient.getCategory());
        adapter.notifyDataSetChanged();
    }

}