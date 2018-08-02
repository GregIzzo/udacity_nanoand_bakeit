package com.example.android.udacity_nanoand_bakeit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;


public class StepNavigationFragment extends Fragment {
    private static final String TAG = "GGG";

    private ImageView prevStepButton;
    private ImageView nextStepButton;
    private ImageView homeButton;

    OnNavClickListener mCallback;
    public interface OnNavClickListener {
        void onNavButtonClicked(String action);//action="prev","next" or "home"
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Check that host has implemented my interface
        try{
            mCallback = (OnNavClickListener) context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString()
            + " must implement OnNavClickListener");
        }
    }

    public StepNavigationFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_navigation_detail, container, false);

        prevStepButton = (ImageView) rootView.findViewById(R.id.iv_prev_button);
        nextStepButton = (ImageView) rootView.findViewById(R.id.iv_next_button);
        homeButton = (ImageView) rootView.findViewById(R.id.iv_home_button);
        prevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "onClick: *** PREV BUTTON CLICKED **");
                //callback
                mCallback.onNavButtonClicked("prev");
            }
        });
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "onClick: *** NEXT BUTTON CLICKED **");
                mCallback.onNavButtonClicked("next");
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d(TAG, "onClick: *** HOME BUTTON CLICKED **");
                mCallback.onNavButtonClicked("home");
            }
        });

        //TextView tv = rootView.findViewById(R.id.tv_stepnavigation);
       // tv.setText("NAVIGATION");


        return rootView;
    }

}
