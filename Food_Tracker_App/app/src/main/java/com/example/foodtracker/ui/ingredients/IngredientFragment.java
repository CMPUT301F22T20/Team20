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


import com.example.foodtracker.databinding.IngredientMainBinding;
import com.example.foodtracker.ui.ingredients.IngredientViewModel;

public class IngredientFragment extends Fragment {

    private IngredientMainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientViewModel ingredientViewModel =
                new ViewModelProvider(this).get(IngredientViewModel.class);

        binding = IngredientMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ListView listview = binding.ingredientList;
        //IngredientViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
