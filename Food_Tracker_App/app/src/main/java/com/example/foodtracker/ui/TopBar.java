package com.example.foodtracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodtracker.MainActivity;
import com.example.foodtracker.R;


public class TopBar extends Fragment {

    private static final String SUPPORT_TOP_BAR_ADD_KEY = "add";
    private static final String TOP_BAR_TITLE_KEY = "title";
    private TopBarListener topBarListener;

    public TopBar() {
        super(R.layout.top_bar);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null && getArguments() != null) {
            TextView topBarTitle = view.findViewById(R.id.top_bar_title);
            Button addButton = view.findViewById(R.id.top_bar_add_button);
            ImageButton backButton = view.findViewById(R.id.top_bar_back_button);
            topBarTitle.setText(getArguments().getString(TOP_BAR_TITLE_KEY));
            boolean supportTopBarAdd = getArguments().getBoolean(SUPPORT_TOP_BAR_ADD_KEY);
            addButton.setVisibility(supportTopBarAdd ? View.VISIBLE : View.GONE);
            if (supportTopBarAdd) {
                topBarListener = (TopBarListener) view.getContext();
                addButton.setOnClickListener(topBarView -> topBarListener.onAddClick());
            }
            backButton.setOnClickListener(topBarView -> {
                Intent mainMenu = new Intent(view.getContext(), MainActivity.class);
                startActivity(mainMenu);
            });
        }
    }

    /**
     * Create a top bar with the given title, and boolean for whether or not this top bar handles adding items
     * @return A new instance of fragment NavBar.
     */
    public static TopBar newInstance(String title, boolean supportAdding) {
        TopBar fragment = new TopBar();
        Bundle args = new Bundle();
        args.putString(TOP_BAR_TITLE_KEY, title);
        args.putBoolean(SUPPORT_TOP_BAR_ADD_KEY, supportAdding);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Provides callbacks for actions done in the top bar
     */
    public interface TopBarListener {

        /**
         * Callback when the add button is clicked on the top bar
         */
        void onAddClick();

    }
}
