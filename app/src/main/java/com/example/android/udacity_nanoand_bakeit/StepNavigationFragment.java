package com.example.android.udacity_nanoand_bakeit;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

public class StepNavigationFragment extends Fragment {
    private static final String TAG = "GGG";
    public StepNavigationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_navigation_detail, container, false);
        TextView tv = rootView.findViewById(R.id.tv_stepnavigation);
        tv.setText("NAVIGATION");


        return rootView;
    }

}
