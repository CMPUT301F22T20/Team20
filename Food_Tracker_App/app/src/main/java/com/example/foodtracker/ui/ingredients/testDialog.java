package com.example.foodtracker.ui.ingredients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.foodtracker.R;

public class testDialog extends DialogFragment {

    //private TextView text;
    private testDialog.OnFragmentInteractionListener listener;


    public interface OnFragmentInteractionListener {

        void onOkPressed(Ingredient newIngredient);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
/*
        if (context instanceof testDialog.OnFragmentInteractionListener) {
            listener = (testDialog.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement OnFragmentInteractionListener");
        }

 */


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.test_dialog, null);

        //text = view.findViewById(R.id.testText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        return builder
                .setView(view)
                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Ingredient egg = new Ingredient("egg");
                        listener.onOkPressed(egg);
                    }
                }).create();

    }
}
