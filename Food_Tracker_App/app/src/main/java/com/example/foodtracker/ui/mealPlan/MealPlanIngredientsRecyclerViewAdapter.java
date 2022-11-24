package com.example.foodtracker.ui.mealPlan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;

import java.util.ArrayList;

public class MealPlanIngredientsRecyclerViewAdapter extends RecyclerView.Adapter<MealPlanIngredientsRecyclerViewAdapter.MealPlanIngredientHolder>{
    private final ArrayList<Ingredient> ingredientArrayList;
    private final Context context;

    public interface MPIngredientArrayListener{
        void deleteIngredient(int ingredientPosition);
    }

   private MPIngredientArrayListener mpIngredientArrayListener;


    MealPlanIngredientsRecyclerViewAdapter(ArrayList<Ingredient> ingredientArrayList, Context context, MPIngredientArrayListener mpIngredientArrayListener) {
        this.ingredientArrayList = ingredientArrayList;
        this.context = context;
        this.mpIngredientArrayListener= mpIngredientArrayListener;
    }


    @NonNull
    @Override
    public MealPlanIngredientsRecyclerViewAdapter.MealPlanIngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_plan_ingredient_content, parent, false);
        return new MealPlanIngredientsRecyclerViewAdapter.MealPlanIngredientHolder(view);
    }

    /**
     * Populates the view with Meal Plan Ingredient information
     */
    @Override
    public void onBindViewHolder(MealPlanIngredientsRecyclerViewAdapter.MealPlanIngredientHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);
        holder.description.setText(ingredient.getDescription());
        holder.category.setText(String.format("%s", ingredient.getCategory()));
        holder.amount.setText(String.format("Quantity: %s", ingredient.getAmount()));
        holder.unit.setText(ingredient.getUnit());

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }


    /**
     * Represents an {@link Ingredient} in our {@link MealPlanIngredientsRecyclerViewAdapter}
     */
    public class MealPlanIngredientHolder extends RecyclerView.ViewHolder {

        protected final TextView description = itemView.findViewById(R.id.mealPlanIngredientName);
        protected final TextView category = itemView.findViewById(R.id.mealPlanIngredientCategory);
        protected final TextView amount = itemView.findViewById(R.id.mealPlanIngredientAmount);
        protected final TextView unit = itemView.findViewById(R.id.mealPlanIngredientUnit);


        protected final ImageButton deleteIngredient = itemView.findViewById(R.id.deleteMealPlanIngredient);



        public MealPlanIngredientHolder(View itemView) {
            super(itemView);

            deleteIngredient.setOnClickListener(onClick -> {
                Ingredient ingredient = ingredientArrayList.get(getAdapterPosition());
                ingredientArrayList.remove(ingredient);
                mpIngredientArrayListener.deleteIngredient(getAdapterPosition());
            });


        }
    }
}
