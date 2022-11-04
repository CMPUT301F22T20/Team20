package com.example.foodtracker.ui.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.ArrayListener;

import java.util.ArrayList;

/**
 * This class creates an adapter for the recycler view of ingredients
 * Copyright: COYG
 *
 * @see <a href=https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example">Stack Overflow</a>
 */
public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.IngredientHolder> {

    /**
     * See {@link ArrayListener}
     * @implNote For safe type casting
     */
    public interface IngredientArrayListener extends ArrayListener<Ingredient> {}

    private final ArrayList<Ingredient> ingredientArrayList;
    private final Context context;
    private final IngredientArrayListener ingredientListener;

    IngredientRecyclerViewAdapter(Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
        ingredientListener = (IngredientArrayListener) context;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_content, parent, false);
        return new IngredientHolder(view, ingredientListener);
    }

    /**
     * Populates the view with Ingredient information
     *
     * @see <a href="https://stackoverflow.com/questions/28408826/textview-visible-invisible-using-one-button-in-android">
     *     Setting textview to visible and invisible </a>
     *  Copyright:  CC BY-SA 3.0 (Pramod Yadav)
     *
     * @see <a href="https://mobikul.com/what-is-expandable-recyclerview-in-android/">Expandable recyclerview </a>
     * Copyright: © Copyright 2010-2022, Webkul Software (Registered in India/USA). All rights reserved.
     * (Ashwani Yadav)
     */
    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);
        holder.name.setText(ingredient.getDescription());
        holder.cost.setText(String.format("Cost: $%s", ingredient.getCost()));
        holder.amount.setText(String.format("Quantity: %s", ingredient.getAmount()));
        holder.expiry.setText(String.format("Expiry Date: %s", ingredient.getExpiry()));
        holder.category.setText(String.format("Category: %s", ingredient.getCategory()));
        holder.location.setText(String.format("Location: %s", ingredient.getLocation()));
        holder.extraIngredientInformation.setOnClickListener(v -> {
            int toggledVisibility = holder.expandIngredient.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            holder.expandIngredient.setVisibility(toggledVisibility);
        });
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    /**
     * Represents an {@link Ingredient} in our {@link IngredientRecyclerViewAdapter}
     */
    public class IngredientHolder extends RecyclerView.ViewHolder {

        protected final TextView name = itemView.findViewById(R.id.ingredient_name);
        protected final TextView cost = itemView.findViewById(R.id.text_ingredient_cost);
        protected final TextView amount = itemView.findViewById(R.id.text_ingredient_amount);
        protected final TextView category = itemView.findViewById(R.id.text_ingredient_category);
        protected final TextView expiry = itemView.findViewById(R.id.text_ingredient_expiry);
        protected final TextView location = itemView.findViewById(R.id.text_ingredient_location);

        protected final RelativeLayout extraIngredientInformation = itemView.findViewById(R.id.relative_layout);
        protected final RelativeLayout expandIngredient = itemView.findViewById(R.id.expand_ingredient);


        IngredientHolder(View itemView, IngredientArrayListener ingredientListener) {
            super(itemView);
            Button deleteIngredient = itemView.findViewById(R.id.delete_ingredient);
            Button editIngredient = itemView.findViewById(R.id.edit_ingredient);
            deleteIngredient.setOnClickListener(onClick -> {
                Ingredient ingredient = ingredientArrayList.get(getAdapterPosition());
                ingredientListener.onDelete(ingredient);
            });
            editIngredient.setOnClickListener(onClick -> {
                Ingredient ingredient = ingredientArrayList.get(getAdapterPosition());
                ingredientListener.onEdit(ingredient);
            });
        }
    }
}
