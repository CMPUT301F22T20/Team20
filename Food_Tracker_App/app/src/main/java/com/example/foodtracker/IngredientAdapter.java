package com.example.foodtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SimpleTimeZone;

/**
 * This is an array adapter that keeps track of {@link Ingredient} Ingredient objects
 */
//TO DO: will have to "expand" the ingredients list
public class IngredientAdapter extends ArrayAdapter<Ingredient> {

    private Context context;
    private ArrayList<Ingredient> IngredientList;

    public IngredientAdapter(Context context,ArrayList<Ingredient> IngredientList){
        super(context,0,IngredientList);
        this.IngredientList = IngredientList;
        this.context = context;

    }

    /**
     * This method displays data of a specified Ingredient in the array of Ingredients
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.ingredient, parent,false);
        }

        Ingredient ingredient = IngredientList.get(position);

        TextView textIngredientDescription = view.findViewById(R.id.textIngredientDescription);
        TextView textIngredientExpiry = view.findViewById(R.id.textIngredientExpiry);
        TextView textIngredientCategory = view.findViewById(R.id.textIngredientCategory);
        TextView textIngredientAmount = view.findViewById(R.id.textIngredientAmount);
        TextView textIngredientQuantity = view.findViewById(R.id.textIngredientQuantity);
        RelativeLayout expandIngredient = view.findViewById(R.id.expandIngredient);

        textIngredientDescription.setText(ingredient.getDescription());
        textIngredientCategory.setText(ingredient.getCategory());
        textIngredientAmount.setText((CharSequence) ingredient.getAmount());//not sure if this should be casted to charSequence
        textIngredientQuantity.setText(ingredient.getQuantity());
        textIngredientExpiry.setText((CharSequence) ingredient.getExpiry());


        return view;

    }

}
