package com.example.foodtracker.ui.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ingredient.Ingredient;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<Ingredient> {

    private ArrayList<Ingredient> ingredients;
    private Context context;

    public CustomList(Context context, ArrayList<Ingredient> ingredients){
        super(context,0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.recipe_ingredient_content, parent,false);
        }
        Ingredient ingredient = ingredients.get(position);

        TextView description = view.findViewById(R.id.Name);
        TextView amount = view.findViewById(R.id.Amount);
        TextView category = view.findViewById(R.id.Category);
        Button deleteButton = view.findViewById(R.id.delete_Button);

        description.setText(ingredient.getDescription());
        String amount_str = String.valueOf(ingredient.getAmount());
        amount.setText(amount_str);
        category.setText(ingredient.getCategory());

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients.remove(position);
                notifyDataSetChanged();
            }
        });
        return view;

    }
}
