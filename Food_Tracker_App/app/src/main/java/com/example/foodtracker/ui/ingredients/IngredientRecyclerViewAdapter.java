package com.example.foodtracker.ui.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.Ingredient;

import java.util.ArrayList;

/**
 * This class creates an adapter for the recycler view of ingredients
 * Copyright: COYG
 *
 * @see <a href=https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example">Stack Overflow</a>
 */
public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder>{

    private final ArrayList<Ingredient> ingredientArrayList;
    private ItemClickListener mClickListener;
    Context context;

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
        holder.textIngredientName.setText(ingredient.getDescription());
        holder.textIngredientCost.setText(String.format("Cost: $%s", ingredient.getCost()));
        holder.textIngredientAmount.setText(String.format("Quantity: %s", ingredient.getAmount()));
        holder.textIngredientExpiry.setText(String.format("Expiry Date: %s",ingredient.getExpiry()));
        holder.textIngredientCategory.setText(String.format("Category: %s",ingredient.getCategory()));
        holder.textIngredientLocation.setText(String.format("Location: %s",ingredient.getLocation()));


        holder.expandIngredient.setVisibility(View.GONE);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.expandIngredient.setVisibility(holder.expandIngredient.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
        });


    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textIngredientName;
        TextView textIngredientCost;
        TextView textIngredientAmount;
        TextView textIngredientCategory;
        TextView textIngredientExpiry;
        TextView textIngredientLocation;

        Button editIngredient;
        Button deleteIngredient;

        RelativeLayout relativeLayout; //when we click on this, trigger an expansion
        RelativeLayout expandIngredient;


        ViewHolder(View itemView) {
            super(itemView);
            textIngredientName= itemView.findViewById(R.id.ingredient_name);
            textIngredientCost = itemView.findViewById(R.id.text_ingredient_cost);
            textIngredientAmount = itemView.findViewById(R.id.text_ingredient_amount);
            textIngredientCategory = itemView.findViewById(R.id.text_ingredient_category);
            textIngredientExpiry = itemView.findViewById(R.id.text_ingredient_expiry);
            textIngredientLocation = itemView.findViewById(R.id.text_ingredient_location);

            Button editIngredient = itemView.findViewById(R.id.edit_ingredient);
            Button deleteIngredient= itemView.findViewById(R.id.delete_ingredient);

            relativeLayout = itemView.findViewById(R.id.relative_layout);
            expandIngredient = itemView.findViewById(R.id.expand_ingredient);

            deleteIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Ingredient ingredient = ingredientArrayList.get(getAdapterPosition());
                    ingredientArrayList.remove(ingredient);
                    notifyDataSetChanged();

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
