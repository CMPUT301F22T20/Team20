package com.example.foodtracker.ui.mealPlan;

import android.content.Context;
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

    public interface MealPlanDayArrayListener{
        //handles operations with MEAL PLAN
        void deleteMealPlan(MealPlanDay mealPlanDay);


        //handles with Ingredient operations
        void deleteIngredient(int ingredientPosition,MealPlanDay object);

        //handles Recipe operations
        void deleteRecipe(int recipePosition,MealPlanDay object);

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
    public class MealPlanDayHolder extends RecyclerView.ViewHolder implements MealPlanIngredientsRecyclerViewAdapter.MPIngredientArrayListener,
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
                mealPlanListener.deleteMealPlan(mealPlan);
            });

        }

        @Override
        public void deleteIngredient(int ingredientPosition) {
            MealPlanDay mealPlanDay = mealPlanDayArrayList.get(getAdapterPosition());
            mealPlanListener.deleteIngredient(ingredientPosition,mealPlanDay);
        }

        @Override
        public void deleteRecipe(int recipePosition) {
            MealPlanDay mealPlanDay = mealPlanDayArrayList.get(getAdapterPosition());
            mealPlanListener.deleteRecipe(recipePosition,mealPlanDay);
        }


    }

}