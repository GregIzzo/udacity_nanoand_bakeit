package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class RecipeStepsActivity extends AppCompatActivity implements RecipeStepsRecyclerAdapter.RecipeStepsAdapterOnClickHandler{

    private static String TAG = "GGG";

    private RecyclerView recyclerView;
    private RecipeStepsRecyclerAdapter recipeStepsRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipesteps_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Log.d(TAG, "RecipeStepsActivity.onCreate. Number of steps=" + RecipeJSON.getCurrRecipeStepCount() );
           // Bundle arguments = new Bundle();
            //no bundle needed. Recipe info is in RecipeJSON (
            //arguments.putInt(RecipeStepsFragment.ARG_RECIPE_INDEX,
            //        getIntent().getIntExtra(RecipeStepsFragment.ARG_RECIPE_INDEX,0));

/* REMOVE FRAGMENT
            RecipeStepsFragment fragment = new RecipeStepsFragment();
           // fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)

                   .commit();

   */

            recyclerView = findViewById(R.id.steps_list);
            assert recyclerView != null;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            if (mLayoutManager == null) Log.d(TAG, "onCreate: LAYOUTMANAGER IS NULL");
            recyclerView.setLayoutManager(mLayoutManager);
            recipeStepsRecyclerAdapter = new RecipeStepsRecyclerAdapter(this);
            recyclerView.setAdapter(recipeStepsRecyclerAdapter);
            recipeStepsRecyclerAdapter.setRecipeData("123");

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int listPosition) {
        Log.d(TAG, "RecipeStepsActivitty onClick: pos=" + listPosition);

       // RecipeJSON.setCurrentRecipe(listPosition);
        //Intent intent = new Intent(this, RecipeStepsActivity.class);
        //    intent.putExtra(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);

        //startActivity(intent);


        if (listPosition == 0){
            //ingredients
            Intent intent = new Intent(this, IngredientListActivity.class);
            startActivity(intent);
        } else {
            RecipeJSON.setCurrentRecipeStep(listPosition-1);
            Intent intent = new Intent(this, StepDetailActivity.class);
            //    intent.putExtra(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);
            startActivity(intent);
        }

    }
}
