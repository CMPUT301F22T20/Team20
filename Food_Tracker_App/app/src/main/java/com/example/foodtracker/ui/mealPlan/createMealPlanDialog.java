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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class createMealPlanDialog extends DialogFragment {


    public interface setMPDatesListener{
        void addMP(ArrayList<String> day);
    }

    private setMPDatesListener mpDatesListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mpDatesListener= (setMPDatesListener) context;
    }


    /**
     * https://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.create_meal_plan_dialog, null);
        DatePicker startDayPicker = view.findViewById(R.id.mealPlanStartDate);
        DatePicker endDayPicker = view.findViewById(R.id.mealPlanEndDate);


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
                        cStart.set(startDayPicker.getYear(), startDayPicker.getMonth(), startDayPicker.getDayOfMonth());

                        Calendar cEnd = Calendar.getInstance();
                        cEnd.set(endDayPicker.getYear(), endDayPicker.getMonth(), endDayPicker.getDayOfMonth());

                        if (cStart.after(cEnd)){
                            String message = "Start day cannot be after end day";
                            Toast.makeText(getContext(),message, Toast.LENGTH_LONG).show();
                        }
                        else {
                            setDates(cStart,cEnd);
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
        dialog.show();

        return dialog;
    }


    public void setDates(Calendar startDay, Calendar endDay) {

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        ArrayList<String> listDates = new ArrayList<>();

        while(startDay.compareTo(endDay) < 1){
            Date convertDate =  startDay.getTime();
            String strDate = dateFormat.format(convertDate);
            startDay.add(Calendar.DAY_OF_MONTH, 1);
            listDates.add(strDate);
        }
        mpDatesListener.addMP(listDates);
    }

}
