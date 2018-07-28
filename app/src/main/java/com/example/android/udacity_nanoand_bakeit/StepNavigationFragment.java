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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //RecipeJSON should have a 'current recipe;
        // the offset into Recipe data is passed in. Make calls to RecipeJSON class for data
        // recipeOffset = getArguments().getInt(ARG_RECIPE_INDEX);
        // myJsonObject = RecipeJSON.getRecipe(recipeOffset);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(RecipeJSON.getCurrRecipeName());
        }
        //   }
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
