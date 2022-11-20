package com.example.foodtracker.ui.mealPlan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.recipe.Recipe;

import java.util.ArrayList;

public class MealPlanRecipesRecyclerViewAdapter extends RecyclerView.Adapter<MealPlanRecipesRecyclerViewAdapter.MealPlanRecipeHolder> {
    private final ArrayList<Recipe> recipeArrayList;

    MealPlanRecipesRecyclerViewAdapter(ArrayList<Recipe> recipeArrayList) {
        this.recipeArrayList = recipeArrayList;
    }


    @NonNull
    @Override
    public MealPlanRecipesRecyclerViewAdapter.MealPlanRecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_plan_recipe_content, parent, false);
        return new MealPlanRecipesRecyclerViewAdapter.MealPlanRecipeHolder(view);
    }

    /**
     * Populates the view with Meal Plan Recipe information
     */
    @Override
    public void onBindViewHolder(MealPlanRecipesRecyclerViewAdapter.MealPlanRecipeHolder holder, int position) {
        Recipe recipe = recipeArrayList.get(position);
        holder.title.setText(recipe.getTitle());
        holder.category.setText(String.format("%s", recipe.getCategory()));
        holder.servings.setText(String.format("Servings: %s", recipe.getServings()));
        holder.prepTime.setText(String.format("Prep Time: %s", recipe.getPrepTime()));
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    /**
     * Represents an {@link Ingredient} in our {@link MealPlanIngredientsRecyclerViewAdapter}
     */
    public class MealPlanRecipeHolder extends RecyclerView.ViewHolder {

        protected final TextView title = itemView.findViewById(R.id.mealPlanRecipeTitle);
        protected final TextView category = itemView.findViewById(R.id.mealPlanRecipeCategory);
        protected final TextView servings = itemView.findViewById(R.id.mealPlanRecipeServings);
        protected final TextView prepTime = itemView.findViewById(R.id.mealPlanRecipePrepTime);

        public MealPlanRecipeHolder(View itemView) {
            super(itemView);
        }
    }
}
