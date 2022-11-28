package com.example.foodtracker.ui.mealPlan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.mealPlan.MealPlanDay;

import java.util.ArrayList;

/**
 * This class creates an adapter for the recycler view of ingredients
 * Copyright: COYG
 *
 * @see <a href=https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example">Stack Overflow</a>
 */
public class MealPlanDayRecyclerViewAdapter extends RecyclerView.Adapter<MealPlanDayRecyclerViewAdapter.MealPlanDayHolder>{

    private AlertDialog mDialog;

    public interface MealPlanDayArrayListener{
        /**
         * Handles deletion of {@link MealPlanDay} objects.
         * @param mealPlanDay
         */
        void deleteMealPlan(MealPlanDay mealPlanDay);

        /**
         * Handles deletion of meal plan ingredients.
         * @param ingredientPosition
         * @param object
         */
        void deleteIngredient(int ingredientPosition,MealPlanDay object);

        /**
         * Handles deletion of meal plan recipes.
         * @param recipePosition
         * @param object
         */
        void deleteRecipe(int recipePosition,MealPlanDay object);

        //handles ingredient amount change
        void scaleIngredient(int ingredientPosition, MealPlanDay object);

        void scaleRecipe(int recipePosition, MealPlanDay object);

    }

    public interface MealPlanArrayListener {
        void onAddIngredientClick(MealPlanDay mealPlan);
        void onAddRecipeClick(MealPlanDay mealPlan);
    }


    private final ArrayList<MealPlanDay> mealPlanDayArrayList;
    private final Context context;
    private final MealPlanDayArrayListener mealPlanListener;
    private MealPlanArrayListener mealPlanArrayListener;



    MealPlanDayRecyclerViewAdapter(Context context, ArrayList<MealPlanDay> mealPlanDayArrayList) {
        this.context = context;
        this.mealPlanDayArrayList = mealPlanDayArrayList;
        mealPlanListener= (MealPlanDayArrayListener) context;
        mealPlanArrayListener = (MealPlanArrayListener) context;
    }


    @NonNull
    @Override
    public MealPlanDayRecyclerViewAdapter.MealPlanDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meal_plan_day_content, parent, false);
        return new MealPlanDayRecyclerViewAdapter.MealPlanDayHolder(view, mealPlanArrayListener);
    }

    /**
     * Populates the view with Meal Plan Day information
     */
    @Override
    public void onBindViewHolder(MealPlanDayRecyclerViewAdapter.MealPlanDayHolder holder, int position) {
        MealPlanDay mealPlanDay = mealPlanDayArrayList.get(position);
        holder.day.setText(mealPlanDay.getDay());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.mealPlanDayIngredientsList.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                holder.mealPlanDayRecipesList.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        layoutManager.setInitialPrefetchItemCount(mealPlanDay.getIngredients().size());
        layoutManager2.setInitialPrefetchItemCount(mealPlanDay.getRecipes().size());

        MealPlanIngredientsRecyclerViewAdapter childItemAdapter = new MealPlanIngredientsRecyclerViewAdapter(mealPlanDay.getIngredients(), context,holder);
        holder.mealPlanDayIngredientsList.setLayoutManager(layoutManager2);
        holder.mealPlanDayIngredientsList.setAdapter(childItemAdapter);

        MealPlanRecipesRecyclerViewAdapter childItemAdapter2 = new MealPlanRecipesRecyclerViewAdapter(mealPlanDay.getRecipes(),context,holder);
        holder.mealPlanDayRecipesList.setLayoutManager(layoutManager);
        holder.mealPlanDayRecipesList.setAdapter(childItemAdapter2);

    }

    @Override
    public int getItemCount() {
        return mealPlanDayArrayList.size();
    }

    /**
     * Represents an {@link MealPlanDay} in our {@link MealPlanDayRecyclerViewAdapter}
     */
    public class MealPlanDayHolder extends RecyclerView.ViewHolder implements
            MealPlanIngredientsRecyclerViewAdapter.MPIngredientArrayListener,
            MealPlanRecipesRecyclerViewAdapter.MPRecipesArrayListener{

        protected final TextView day = itemView.findViewById(R.id.mealPlanDay);
        protected RecyclerView mealPlanDayIngredientsList = itemView.findViewById(R.id.mealPlanIngredientsList);
        protected RecyclerView mealPlanDayRecipesList = itemView.findViewById(R.id.mealPlanRecipesList);
        protected ImageButton mealPlanDayDelete = itemView.findViewById(R.id.mealPlanDeleteDayButton);

        public MealPlanDayHolder(View itemView, MealPlanArrayListener mealPlanArrayListener) {
            super(itemView);
            Button mealPlanAddIngredientButton = itemView.findViewById(R.id.mealPlanAddIngredientButton);
            Button mealPlanAddRecipeButton = itemView.findViewById(R.id.mealPlanAddRecipeButton);

            mealPlanAddIngredientButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MealPlanDay mealPlan = mealPlanDayArrayList.get(getAdapterPosition());
                    mealPlanArrayListener.onAddIngredientClick(mealPlan);
                }
            });

            mealPlanAddRecipeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MealPlanDay mealPlan = mealPlanDayArrayList.get(getAdapterPosition());
                    mealPlanArrayListener.onAddRecipeClick(mealPlan);
                }
            });

            mealPlanDayDelete.setOnClickListener(onClick -> {
                MealPlanDay mealPlan = mealPlanDayArrayList.get(getAdapterPosition());
                confirmDelete(itemView.getContext(),mealPlan);
            });

        }

        @Override
        public void deleteIngredient(int ingredientPosition) {
            MealPlanDay mealPlanDay = mealPlanDayArrayList.get(getAdapterPosition());
            mealPlanListener.deleteIngredient(ingredientPosition,mealPlanDay);
        }

        @Override
        public void scaleIngredient(int ingredientPosition) {
            MealPlanDay mealPlanDay = mealPlanDayArrayList.get(getAdapterPosition());
            mealPlanListener.scaleIngredient(ingredientPosition, mealPlanDay);
        }

        @Override
        public void deleteRecipe(int recipePosition) {
            MealPlanDay mealPlanDay = mealPlanDayArrayList.get(getAdapterPosition());
            mealPlanListener.deleteRecipe(recipePosition,mealPlanDay);
        }

        @Override
        public void scaleRecipe(int recipePosition) {
            MealPlanDay mealPlanDay = mealPlanDayArrayList.get(getAdapterPosition());
            mealPlanListener.scaleRecipe(recipePosition,mealPlanDay);
        }


    }


    /**
     * Confirms with the user if they would like to delete a day from the meal plan
     * @param context
     * @param mealPlan
     */
    private void confirmDelete(Context context,MealPlanDay mealPlan){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete Date");
        builder.setMessage("Are you sure you want to delete this date from your meal plan?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mealPlanListener.deleteMealPlan(mealPlan);
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }




}

