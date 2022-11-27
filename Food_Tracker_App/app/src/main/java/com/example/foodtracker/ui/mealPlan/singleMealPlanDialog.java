package com.example.foodtracker.ui.mealPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class singleMealPlanDialog extends DialogFragment {

    public interface setSingleMPDatesListener{
        void addSingle(String day);
        boolean isInList(String day);
    }

    setSingleMPDatesListener singleMPDatesListener;
    public static final String SINGLE_MEAL_PLAN_TAG = "Create_single_meal_plan";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        singleMPDatesListener = (setSingleMPDatesListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.single_meal_plan_dialog,null);
        DatePicker singleDate = view.findViewById(R.id.mealPlanSingleDate);


        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Create meal plan")
                .setPositiveButton("Add", null) //Set to null. We override the onclick
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Calendar cStart = Calendar.getInstance();
                        cStart.set(singleDate.getYear(), singleDate.getMonth(), singleDate.getDayOfMonth());
                        String entryDay = String.format(Locale.CANADA, "%02d-%02d-%d",
                                singleDate.getMonth() + 1,singleDate.getDayOfMonth(), singleDate.getYear());

                        if (singleMPDatesListener.isInList(entryDay) == false){
                            setDay(cStart);
                            dialog.dismiss();
                        }
                        else {
                            String message = "This day is already in the meal plan.";
                            Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        dialog.show();
        return dialog;

    }

    public void setDay(Calendar singleDay) {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date convertDate =  singleDay.getTime();
        String strDate = dateFormat.format(convertDate);

        singleMPDatesListener.addSingle(strDate);
    }


}


