package com.example.android.udacity_nanoand_bakeit;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepsActivity}
 * on handsets.
 */
public class StepInstructionsFragment extends Fragment  {

    public static final String ARG_RECIPE_INDEX = "recipe_index";
    private static final String TAG = "GGG";
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepInstructionsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_directions_view, container, false);
        //tv_stepinstructions

        String s = RecipeJSON.getCurrRecipeStepDescription(RecipeJSON.getCurrentRecipeStepNum());
        Log.d(TAG, "onCreateView: $$$ stepnum="+RecipeJSON.getCurrentRecipeStepNum()+" descrip["+s+"]");
        TextView tv = rootView.findViewById(R.id.tv_stepinstructions);
        tv.setText(s);


        return rootView;
    }


}
