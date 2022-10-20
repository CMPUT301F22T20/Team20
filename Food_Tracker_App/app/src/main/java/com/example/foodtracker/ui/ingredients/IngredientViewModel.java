package com.example.foodtracker.ui.ingredients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class IngredientViewModel extends ViewModel {

    /*
    private String ingredient_tag = IngredientViewModel.class.getName();

    private MutableLiveData<ArrayList<Ingredient>> ingredientList;

    MutableLiveData<ArrayList<Ingredient>> getIngredients() {
        if (ingredientList == null) {
            ingredientList = new MutableLiveData<>();
            loadIngredients;
        }
        return ingredientList;
    }

    private void loadIngredients() {

    }
    */
    private final MutableLiveData<String> mText;

    public IngredientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ingredient fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
