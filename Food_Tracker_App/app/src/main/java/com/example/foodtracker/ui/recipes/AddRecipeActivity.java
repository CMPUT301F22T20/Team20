package com.example.foodtracker.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;
import com.example.foodtracker.model.Recipe;
import com.example.foodtracker.ui.ingredients.IngredientRecyclerViewAdapter;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity
        implements AddIngredient.smallIngredientListener{

    private EditText titleField;
    private EditText timeField;
    private EditText servingsField;
    private EditText categoryField;
    private ListView ingredientsListField;
    private EditText commentsField;
    private Button addIngredientButton, confirmButton, cancelButton;

    private ArrayList<Ingredient> arrayList;
    private ArrayAdapter<Ingredient> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        titleField = findViewById(R.id.recipeTitle);
        timeField = findViewById(R.id.recipePrepTime);
        servingsField = findViewById(R.id.recipeServings);
        categoryField = findViewById(R.id.recipeCategory);
        ingredientsListField = findViewById(R.id.ingredients);
        commentsField = findViewById(R.id.recipeComments);

        addIngredientButton = findViewById(R.id.addIngredient_Button);
        confirmButton = findViewById(R.id.addIngredientConfirm);
        cancelButton = findViewById(R.id.cancelAddIngredient);

        arrayList = new ArrayList<>();

        adapter = new CustomList(this, arrayList);
        ingredientsListField.setAdapter(adapter);


        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddIngredient().show(getSupportFragmentManager(), "Add_ingredient");
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Recipe recipe = createRecipe();

                //send the recipe to main screen
                Intent intent = new Intent(getApplicationContext(), RecipesMainScreen.class);
                intent.putExtra("recipe_key", recipe);
                //finish();
                startActivity(intent);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private Recipe createRecipe() {
        Recipe recipe = new Recipe();

        recipe.setImage("");
        recipe.setTitle(titleField.getText().toString());

        String time_str = timeField.getText().toString();
        try{
            int time_int = Integer.parseInt(time_str);
            recipe.setPrepTime(time_int);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String serving_str = servingsField.getText().toString();
        try {
            int servings_int = Integer.parseInt(serving_str);
            recipe.setServings(servings_int);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        recipe.setCategory(categoryField.getText().toString());
        recipe.setComment(commentsField.getText().toString());

        recipe.setIngredients(arrayList);

        return recipe;
    }

    @Override
    public void addRecipeIngredient(Ingredient new_ingredient) {
        adapter.add(new_ingredient);
    }
}