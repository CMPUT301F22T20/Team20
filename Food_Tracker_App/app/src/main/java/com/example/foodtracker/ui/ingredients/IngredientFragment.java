package com.example.foodtracker.ui.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodtracker.databinding.IngredientMainBinding;
import com.example.foodtracker.ui.ingredients.IngredientViewModel;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    private IngredientMainBinding binding;
    private ArrayList<Ingredient> ingredientList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientViewModel ingredientViewModel =
                new ViewModelProvider(this).get(IngredientViewModel.class);

        binding = IngredientMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final RecyclerView recyclerView = binding.ingredientList;

        // initialize adapter ingredient
        IngredientRecyclerViewAdapter ingredientAdapter = new IngredientRecyclerViewAdapter(
                this.getContext(),ingredientList);
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setHasFixedSize(true);
        //IngredientViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
