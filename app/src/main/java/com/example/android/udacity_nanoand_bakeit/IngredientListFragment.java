package com.example.android.udacity_nanoand_bakeit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

public class IngredientListFragment extends Fragment implements IngredientListRecyclerAdapter.IngredientListAdapterOnClickHandler {

    public static final String CURR_RECIPE_INDEX = "current_recipe_index";
    private RecyclerView recyclerView;
    private IngredientListRecyclerAdapter ingredientListRecyclerAdapter;

    private int recipeIndex = -1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IngredientListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_ingredients_list, container, false);

        if (savedInstanceState != null){
            recipeIndex = savedInstanceState.getInt(CURR_RECIPE_INDEX);
            RecipeJSON.setCurrentRecipe(recipeIndex);
        } else {
            recipeIndex = RecipeJSON.getCurrentRecipeListPosition();
        }

        recyclerView = rootView.findViewById(R.id.list_container);
        assert recyclerView != null;

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        if (mLayoutManager == null) Log.d("GGG", "IngredientListFragment.onCreate: LAYOUTMANAGER IS NULL");
        recyclerView.setLayoutManager(mLayoutManager);
        ingredientListRecyclerAdapter = new IngredientListRecyclerAdapter(this);
        recyclerView.setAdapter(ingredientListRecyclerAdapter);
        ingredientListRecyclerAdapter.setRecipeData("123");

        return rootView;
    }

    @Override
    public void onClick(int listPosition) {

    }
    @Override
    public void onSaveInstanceState(Bundle currentState ) {
        currentState.putInt(CURR_RECIPE_INDEX, recipeIndex);
    }

}
