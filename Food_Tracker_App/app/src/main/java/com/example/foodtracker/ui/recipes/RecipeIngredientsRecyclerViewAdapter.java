package com.example.foodtracker.ui.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;

import java.util.ArrayList;

/**
 * This is a custom adapter for the recycler view for ingredients inside the recipe
 */
public class RecipeIngredientsRecyclerViewAdapter extends RecyclerView.Adapter<RecipeIngredientsRecyclerViewAdapter.RecipeIngredientHolder> {
    private final ArrayList<Ingredient> ingredientArrayList;
    private final Context context;

    RecipeIngredientsRecyclerViewAdapter(Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }


    @NonNull
    @Override
    public RecipeIngredientsRecyclerViewAdapter.RecipeIngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_ingredient_content, parent, false);
        return new RecipeIngredientsRecyclerViewAdapter.RecipeIngredientHolder(view);
    }

    /**
     * Populates the view with Recipe Ingredient information
     */
    @Override
    public void onBindViewHolder(RecipeIngredientsRecyclerViewAdapter.RecipeIngredientHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);
        holder.description.setText(ingredient.getDescription());
        holder.amount.setText(String.format("%s", ingredient.getAmount()));
        holder.unit.setText(ingredient.getUnit());
        holder.category.setText(String.format("%s", ingredient.getCategory()));
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    /**
     * Represents an {@link Ingredient} in our {@link RecipeIngredientsRecyclerViewAdapter}
     */
    public class RecipeIngredientHolder extends RecyclerView.ViewHolder {

        protected final TextView description = itemView.findViewById(R.id.Name);
        protected final TextView amount = itemView.findViewById(R.id.Amount);
        protected final TextView unit = itemView.findViewById(R.id.Unit);
        protected final TextView category = itemView.findViewById(R.id.Category);

        public RecipeIngredientHolder(View itemView) {
            super(itemView);
        }
    }
}
