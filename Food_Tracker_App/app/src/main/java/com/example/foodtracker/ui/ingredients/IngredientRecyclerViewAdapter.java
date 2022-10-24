package com.example.foodtracker.ui.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;

import java.util.ArrayList;

/**
 * This class creates an adapter for the recycler view of ingredients
 * Copyright: COYG
 *      @https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 */
public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Ingredient> ingredientArrayList;
    Context context;
    private ItemClickListener mClickListener;

    IngredientRecyclerViewAdapter (Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.context = context;
        this.ingredientArrayList = ingredientArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_content, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);
        holder.myTextView.setText(ingredient.getDescription());
        holder.textIngredientCost.setText(String.valueOf(ingredient.getCost()));
        holder.textIngredientAmount.setText(String.valueOf(ingredient.getAmount()));
        holder.textIngredientExpiry.setText(ingredient.getExpiry());
        holder.textIngredientCategory.setText(ingredient.getCategory());
        holder.textIngredientLocation.setText(ingredient.getLocation());



        boolean isVisible = ingredient.visible;
        holder.expandIngredient.setVisibility(isVisible ? View.VISIBLE:View.GONE);

        holder.myTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Ingredient ingredientPos = ingredientArrayList.get(holder.getAdapterPosition());
                ingredientPos.setVisible(!ingredientPos.isVisible());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView myTextView;
        TextView textIngredientCost;
        TextView textIngredientAmount;
        TextView textIngredientCategory;
        TextView textIngredientExpiry;
        TextView textIngredientLocation;

        LinearLayout linearLayout; //when we click on this, trigger an expansion
        RelativeLayout expandIngredient;




        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.ingredient_name);
            textIngredientCost = itemView.findViewById(R.id.text_ingredient_cost);
            textIngredientAmount = itemView.findViewById(R.id.text_ingredient_amount);
            textIngredientCategory = itemView.findViewById(R.id.text_ingredient_category);
            textIngredientExpiry = itemView.findViewById(R.id.text_ingredient_expiry);
            textIngredientLocation = itemView.findViewById(R.id.text_ingredient_location);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            expandIngredient = itemView.findViewById(R.id.expand_ingredient);

        }

    }

    // convenience method for getting data at click position
    Ingredient getItem(int id) {
        return ingredientArrayList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
