package com.example.foodtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//TO DO: will have to "expand" the ingredients list
public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private ArrayList<Ingredient> IngredientList;

    public IngredientAdapter(Context context,ArrayList<Ingredient> IngredientList){
        super(context,0,IngredientList);
        this.IngredientList = IngredientList;
        this.context = context;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.ingredient, parent,false);
        }

        Ingredient ingredient = IngredientList.get(position);

        TextView ingredientDescription = view.findViewById(R.id.ingredientDescription);
        TextView ingredientExpiry = view.findViewById(R.id.ingredientExpiry);
        TextView ingredientCategory = view.findViewById(R.id.ingredientCategory);
        TextView ingredientAmount = view.findViewById(R.id.ingredientAmount);
        TextView ingredientQuantity = view.findViewById(R.id.ingredientQuantity);
        RelativeLayout expandIngredient = view.findViewById(R.id.expand_ingredients);

        ingredientDescription.setText(ingredient.getDescription());
        ingredientExpiry.setText(ingredient.getExpiry());
        ingredientCategory.setText(ingredient.getCategory());
        ingredientAmount.setText(ingredient.getAmount());
        ingredientQuantity.setText(ingredient.getQuantity());


        return view;

    }

}
