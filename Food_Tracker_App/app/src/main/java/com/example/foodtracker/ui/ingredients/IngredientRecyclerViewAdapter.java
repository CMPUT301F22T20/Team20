package com.example.foodtracker.ui.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;

import java.util.ArrayList;

/**
 * This class creates an adapter for the recycler view of ingredients. The items in recycler view expand
 * through a boolean. Once an item is clicked, the boolean value is changed, and
 * ingredient values are set to "visible"
 * Copyright: COYG
 *      @https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
 * Copyright:
 *      @https://www.youtube.com/watch?v=pGi02uJre4M&ab_channel=AndroidWorldClub
 *
 */
public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder>{

    private ArrayList<Ingredient> ingredientArrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    IngredientRecyclerViewAdapter (Context context, ArrayList<Ingredient> ingredientArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.ingredientArrayList = ingredientArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ingredient, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);

        holder.textIngredientDescription.setText(ingredient.getDescription());
        holder.textIngredientCost.setText(ingredient.getCost().toString());
        holder.textIngredientAmount.setText(ingredient.getAmount());
        holder.textIngredientCategory.setText(ingredient.getCategory());
        holder.textIngredientExpiry.setText(ingredient.getExpiry().toString());
        holder.textIngredientLocation.setText(ingredient.getLocation());

        boolean isExpanded = ingredientArrayList.get(position).isExpanded();
        holder.recyclerIngredientList.setVisibility(isExpanded ? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textIngredientDescription;
        TextView textIngredientCost;
        TextView textIngredientAmount;
        TextView textIngredientCategory;
        TextView textIngredientExpiry;
        TextView textIngredientLocation;

        LinearLayout linearLayout;
        RecyclerView recyclerIngredientList;

        ViewHolder(View itemView) {
            super(itemView);
            textIngredientDescription = itemView.findViewById(R.id.textIngredientDescription);
            textIngredientCost = itemView.findViewById(R.id.textIngredientCost);
            textIngredientAmount = itemView.findViewById(R.id.textIngredientAmount);
            textIngredientCategory = itemView.findViewById(R.id.textIngredientCategory);
            textIngredientExpiry = itemView.findViewById(R.id.textIngredientExpiry);
            textIngredientLocation = itemView.findViewById(R.id.textIngredientLocation);


            linearLayout = itemView.findViewById(R.id.linearLayout);
            recyclerIngredientList = itemView.findViewById(R.id.ingredient_list);

            //itemView.setOnClickListener(this);
            //When container is clicked, it expands to show details
            linearLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Ingredient ingredient = ingredientArrayList.get(getAdapterPosition());
                    ingredient.setExpanded(!ingredient.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });


        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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
