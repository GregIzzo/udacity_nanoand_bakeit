package com.example.android.udacity_nanoand_bakeit;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

import org.json.JSONObject;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepsActivity}
 * on handsets.
 */
public class StepDescriptionFragment extends Fragment  {

    public static final String ARG_RECIPE_INDEX = "recipe_index";
    private static final String TAG = "GGG";
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDescriptionFragment() {
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
        View rootView = inflater.inflate(R.layout.step_directions_detail, container, false);
        //exoplayer

        //tv_stepinstructions
        String s = RecipeJSON.getCurrRecipeStepDescription(RecipeJSON.getCurrentRecipeStepNum());
        TextView tv = rootView.findViewById(R.id.tv_stepinstructions);
        tv.setText(s);


        return rootView;
    }


}
