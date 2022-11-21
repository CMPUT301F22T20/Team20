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
import com.example.foodtracker.model.recipe.Recipe;
import com.example.foodtracker.ui.recipes.RecipeRecyclerViewAdapter;
import com.example.foodtracker.ui.recipes.RecyclerViewInterface;

import java.text.CollationElementIterator;
import java.util.ArrayList;

public class ShoppingCategoryRecyclerViewAdapter extends RecyclerView.Adapter<ShoppingCategoryRecyclerViewAdapter.shoppingCategoryHolder>{

    public interface ShoppingCategoryArrayListener extends ArrayListener<String> {}

    private final ArrayList<String> shoppingCategoryArrayList;
    private final Context context;
    private final ShoppingCategoryRecyclerViewAdapter.ShoppingCategoryArrayListener shoppingListener;
    private final RecyclerViewInterface recyclerViewInterface;

    ShoppingCategoryRecyclerViewAdapter(Context context, ArrayList<String> shoppingCategoryArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.shoppingCategoryArrayList = shoppingCategoryArrayList;
        shoppingListener = (ShoppingCategoryRecyclerViewAdapter.ShoppingCategoryArrayListener) context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ShoppingCategoryRecyclerViewAdapter.shoppingCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shopping_cart_content, parent, false);
        return new ShoppingCategoryRecyclerViewAdapter.shoppingCategoryHolder(view, shoppingListener, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(ShoppingCategoryRecyclerViewAdapter.shoppingCategoryHolder holder, int position) {
        String category = shoppingCategoryArrayList.get(position);
        holder.category.setText(String.format("Category: %s", category));
    }

    @Override
    public int getItemCount() {
        return shoppingCategoryArrayList.size();
    }

    public class shoppingCategoryHolder extends RecyclerView.ViewHolder {

        protected final TextView category = itemView.findViewById(R.id.shopping_category_name);

        public shoppingCategoryHolder(View itemView, ShoppingCategoryRecyclerViewAdapter.ShoppingCategoryArrayListener shoppingListener, RecyclerViewInterface recyclerViewInterface) {
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
