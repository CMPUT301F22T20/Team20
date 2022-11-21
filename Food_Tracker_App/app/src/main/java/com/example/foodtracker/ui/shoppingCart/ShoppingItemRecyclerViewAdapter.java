package com.example.foodtracker.ui.shoppingCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodtracker.R;
import com.example.foodtracker.model.ArrayListener;
import com.example.foodtracker.model.ingredient.Ingredient;
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.recipes.RecipeRecyclerViewAdapter;
import com.example.foodtracker.ui.recipes.RecyclerViewInterface;

import java.text.CollationElementIterator;
import java.util.ArrayList;

public class ShoppingItemRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingItemRecyclerViewAdapter.shoppingItemHolder>{

    public interface ShoppingItemArrayListener extends ArrayListener<Ingredient> {}

    private final ArrayList<Ingredient> shoppingItemArrayList;
    private final Context context;
    private final ShoppingItemRecyclerViewAdapter.ShoppingItemArrayListener shoppingListener;
    private final RecyclerViewInterface recyclerViewInterface;

    ShoppingItemRecyclerViewAdapter(Context context, ArrayList<Ingredient> shoppingItemArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.shoppingItemArrayList = shoppingItemArrayList;
        shoppingListener = (ShoppingItemRecyclerViewAdapter.ShoppingItemArrayListener) context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ShoppingItemRecyclerViewAdapter.shoppingItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_cart_content_item, parent, false);
        return new ShoppingItemRecyclerViewAdapter.shoppingItemHolder(view, shoppingListener, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(ShoppingItemRecyclerViewAdapter.shoppingItemHolder holder, int position) {
        Ingredient ingredient = shoppingItemArrayList.get(position);
//        holder.name.setText(String.format("Category: %s", category));
    }

    @Override
    public int getItemCount() {
        return shoppingItemArrayList.size();
    }

    public class shoppingItemHolder extends RecyclerView.ViewHolder {

        protected final TextView name = itemView.findViewById(R.id.shopping_item_name);
        protected final TextView count = itemView.findViewById(R.id.shopping_item_count);
        protected final TextView unit = itemView.findViewById(R.id.shopping_item_unit);

        public shoppingItemHolder(View itemView, ShoppingItemRecyclerViewAdapter.ShoppingItemArrayListener shoppingListener, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
