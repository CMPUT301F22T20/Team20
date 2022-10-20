package com.example.foodtracker.ui.ingredients;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodtracker.R;
import com.example.foodtracker.databinding.IngredientMainBinding;
import com.example.foodtracker.ui.ingredients.IngredientViewModel;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    private ArrayList<Ingredient> ingredientArrayList;
    private RecyclerView ingredientRV;

    FragmentActivity listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    //private IngredientMainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*
        IngredientViewModel ingredientViewModel =
                new ViewModelProvider(this).get(IngredientViewModel.class);

        binding = IngredientMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final RecyclerView recyclerView = binding.ingredientList;
        //IngredientViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;

         */


       return inflater.inflate(R.layout.ingredient_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialize();
        ingredientRV = view.findViewById(R.id.ingredient_list);
        ingredientRV.setLayoutManager(new LinearLayoutManager(getContext()));

        IngredientRecyclerViewAdapter adapter = new IngredientRecyclerViewAdapter(getContext(),ingredientArrayList);

        ingredientRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void dataInitialize() {

        ingredientArrayList = new ArrayList<>();
        Ingredient tuna = new Ingredient("tuna");
        Ingredient apple = new Ingredient("apple");
        Ingredient broccoli = new Ingredient("broccoli");
        ingredientArrayList.add(tuna);
        ingredientArrayList.add(apple);
        ingredientArrayList.add(broccoli);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

}
