package com.example.android.udacity_nanoand_bakeit;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

import org.json.JSONObject;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link RecipeStepsActivity}
 * on handsets.
 */
public class RecipeStepsFragment extends Fragment  implements RecipeStepsRecyclerAdapter.RecipeStepsAdapterOnClickHandler{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_RECIPE_INDEX = "recipe_index";
    private static final String TAG = "GGG";
    private RecyclerView recyclerView;
    private RecipeStepsRecyclerAdapter recipeStepsRecyclerAdapter;


    private JSONObject myJsonObject;
    //private int recipeOffset;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeStepsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //RecipeJSON should have a 'current recipe;

     //   if (getArguments().containsKey(ARG_RECIPE_INDEX)) {
            // the offset into Recipe data is passed in. Make calls to RecipeJSON class for data
           // recipeOffset = getArguments().getInt(ARG_RECIPE_INDEX);
           // myJsonObject = RecipeJSON.getRecipe(recipeOffset);
        myJsonObject = RecipeJSON.getCurrentRecipe();

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
        View rootView = inflater.inflate(R.layout.steps_list, container, false);

        recyclerView = rootView.findViewById(R.id.steps_list);
        assert recyclerView != null;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        if (mLayoutManager == null) Log.d(TAG, "onCreate: LAYOUTMANAGER IS NULL");
        recyclerView.setLayoutManager(mLayoutManager);
        recipeStepsRecyclerAdapter = new RecipeStepsRecyclerAdapter(this);
        recyclerView.setAdapter(recipeStepsRecyclerAdapter);
        recipeStepsRecyclerAdapter.setRecipeData("123");


/*
        if (myJsonObject != null) {
            int stepCount = RecipeJSON.getCurrRecipeStepCount();
            String out = "";
            for (int i=0; i< stepCount; i++){
                out += "Step:" + RecipeJSON.getCurrRecipeStepId(i) + " - [" + RecipeJSON.getCurrRecipeStepShortDescription(i) + "] "+ RecipeJSON.getCurrRecipeStepDescription(i);
            }
            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(out);
        }
*/
        return rootView;
    }

    @Override
    public void onClick(int listPosition) {
        Log.d(TAG, "onClick: step list position =" + listPosition);

        if (listPosition == 0){
            //ingredients
            Intent intent = new Intent(getActivity(), IngredientListActivity.class);
            startActivity(intent);
        } else {
            RecipeJSON.setCurrentRecipeStep(listPosition-1);
            Intent intent = new Intent(getActivity(), StepDetailActivity.class);
            //    intent.putExtra(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);
            startActivity(intent);
        }
    }

}
