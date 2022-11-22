package com.example.foodtracker.ui.mealPlan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Category;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.ingredient.Location;
import com.example.foodtracker.utils.Collection;

import java.util.ArrayList;

public class IngredientList extends AppCompatActivity {

    ListView ingredientListView;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayAdapter<Ingredient> adapter;
    /**
     * This is a private final variable
     * This holds a collection of {@link Ingredient} objects and is of type {@link Ingredient}
     */
    private final Collection<Ingredient> ingredientsCollection = new Collection<>(Ingredient.class, new Ingredient());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientArrayList = new ArrayList<>();
        adapter = new IngredientListAdapter(this, ingredientArrayList);
        ingredientListView.setAdapter(adapter);
        getIngredients();

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    /**
     * Retrieves ingredients from firestore and populates a string array with the content
     */
    private void getIngredients() {
        ingredientsCollection.getAll(list -> {
            for (Ingredient ingredient : list) {
                ingredientArrayList.add(ingredient);
                adapter.notifyDataSetChanged();
            }
        });
    }
}