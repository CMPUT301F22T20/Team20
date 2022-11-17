package com.example.foodtracker.ui.mealPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;

public class createMealPlanDialog extends DialogFragment {



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.create_meal_plan_dialog, null);
        DatePicker picker = view.findViewById(R.id.mealPlanStartDate);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Set your meal plan dates")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (dialog, i) -> {
                    String y = String.valueOf(picker.getYear());
                    Toast.makeText(getContext(),y, Toast.LENGTH_LONG).show();

                })
                .create();
    }


    public interface createMealPlanDialogListener {
    }
}
