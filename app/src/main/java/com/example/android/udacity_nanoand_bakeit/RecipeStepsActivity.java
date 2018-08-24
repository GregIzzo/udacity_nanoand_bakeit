package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link MainActivity}.
 */
public class RecipeStepsActivity extends AppCompatActivity
        implements RecipeStepsRecyclerAdapter.RecipeStepsAdapterOnClickHandler,
        StepNavigationFragment.OnNavClickListener
{

    public static final String CURR_RECIPE_INDEX = "current_recipe_index";
    public static final String CURR_RECIPE_INGREDIENTSSHOWING = "current_recipe_ingredients_showing";

    private static String TAG = "GGG";

    private RecyclerView recyclerView;
    private RecipeStepsRecyclerAdapter recipeStepsRecyclerAdapter;
    private int recipeIndex = -1;
    private boolean ingredientsShowing = false;

    //mTwoPane : True=two pane view, false=one pane view
    //
    private boolean mTwoPane;
    //Used for 2 pane mode - duplicating functionality from StepDetailActivity
    MediaPlayerFragment playerFragment;
    StepInstructionsFragment stepInstructionsFragment;
    StepNavigationFragment stepNavigationFragment;
    IngredientListFragment stepIngredientsFragment;
    FrameLayout ingredientsFrame;
    FrameLayout mediaPlayerFrame ;
    FrameLayout recipeInstructionsFrame;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipesteps_list);

        if (savedInstanceState != null){
            Log.d(TAG, "SSSSS onCreate: picked from savedinstance="+savedInstanceState.getInt(CURR_RECIPE_INDEX));
            recipeIndex = savedInstanceState.getInt(CURR_RECIPE_INDEX);
            ingredientsShowing = savedInstanceState.getBoolean(CURR_RECIPE_INGREDIENTSSHOWING);
            RecipeJSON.setCurrentRecipe(recipeIndex);
            RecipeJSON.setShowingIngredients(ingredientsShowing);
        } else {
            Log.d(TAG, "SSSSS onCreate: savedInstance=null, recipejson position = "+ RecipeJSON.getCurrentRecipeListPosition());
            recipeIndex = RecipeJSON.getCurrentRecipeListPosition();
            ingredientsShowing =RecipeJSON.isShowingIngredients();
        }


        if(findViewById(R.id.step_detail_container) != null) {
            //Dual Pane Mode
            mTwoPane = true;
            //
            // (1) change itemClicks to NOT open StepDetailActivity
            // (2) Manipulate fragments using same code used by StepDetailActivity
             ingredientsFrame = findViewById(R.id.ingredients_container);
             mediaPlayerFrame = findViewById(R.id.mediaplayer_container);
             recipeInstructionsFrame = findViewById(R.id.instructions_container);
        } else {
            //Single Pane Mode
            mTwoPane = false;

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(RecipeJSON.getCurrRecipeName());
        }
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (findViewById(R.id.step_detail_container) != null){
            Log.d(TAG, "onCreate: *** TWO PANE LAYOUT ****");
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
/////////////////////////////////////////////        if (savedInstanceState == null) {
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
        if (mTwoPane) {
            //dual pane mode, setup the fragments
            showStep("new");
            /*
            if (savedInstanceState == null) {
                showStep("new");
            } else {
                showStep("restore");
            }
            */
        }

//////////////////////////        }
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

            //single pane mode, launch new activity
            if (listPosition == 0) {
                //ingredients
                RecipeJSON.setCurrentRecipeStep(0);
                RecipeJSON.setShowingIngredients(true);
                if (mTwoPane) {

                    showStep("replace");
                } else {
                    Intent intent = new Intent(this, StepDetailActivity.class);
                    //    intent.putExtra(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);
                    startActivity(intent);
                }
            } else {
                RecipeJSON.setCurrentRecipeStep(listPosition - 1);
                RecipeJSON.setShowingIngredients(false);
                if (mTwoPane) {
                    showStep("replace");
                } else {
                    Intent intent = new Intent(this, StepDetailActivity.class);
                    //    intent.putExtra(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);
                    startActivity(intent);
                }
            }

    }

    @Override
    protected void onStop() {
        super.onStop();
        getIntent().putExtra(CURR_RECIPE_INDEX, RecipeJSON.getCurrentRecipeListPosition());
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "SSSSSSS onResume:getIntent().getIntExtra(CURR_RECIPE_INDEX="+getIntent().getIntExtra(CURR_RECIPE_INDEX, 0));
        if (getIntent() != null ){
            RecipeJSON.setCurrentRecipe(getIntent().getIntExtra(CURR_RECIPE_INDEX, 0));
        }
    }
*/
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURR_RECIPE_INDEX, RecipeJSON.getCurrentRecipeListPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(CURR_RECIPE_INDEX)){
            RecipeJSON.setCurrentRecipe(savedInstanceState.getInt(CURR_RECIPE_INDEX));
        }
    }

    /*
    Event listener /interface - called when a nav button in StepNavigationFragment is clicked
    NEEDED FOR DUAL-PANE MODE, TO REACT TO NAVIGATION FRAGMENT
     */
    @Override
    public void onNavButtonClicked(String action) {
        Log.d(TAG, "onNavButtonClicked: ----- INSIDE STEPDETAILACTIVITY. nav=" + action);
        switch(action){
            case "home":
                navigateUpTo(new Intent(this, RecipeStepsActivity.class));
                break;
            case "prev":
                if (RecipeJSON.getCurrentRecipeStepNum() > 0){
                    //decrement step
                    RecipeJSON.setCurrentRecipeStep(RecipeJSON.getCurrentRecipeStepNum() - 1);
                    showStep("replace");
                } else {
                    //Ingredients come before steps, so launch IngredientListActivity
                    RecipeJSON.setShowingIngredients(true);
                    /*
                    Intent intent = new Intent(this, IngredientListActivity.class);
                    startActivity(intent);
                    */
                    showStep("replace");
                }
                break;
            case "next":
                int currstep = RecipeJSON.getCurrentRecipeStepNum();

                if (currstep < RecipeJSON.getCurrRecipeStepCount() - 1){
                    //go to next step
                    if (RecipeJSON.isShowingIngredients()){
                        RecipeJSON.setCurrentRecipeStep(0);
                        RecipeJSON.setShowingIngredients(false);
                    } else {
                        RecipeJSON.setCurrentRecipeStep(RecipeJSON.getCurrentRecipeStepNum() + 1);
                    }
                    showStep("replace");
                } else {
                    //Cant go further. Do nothing
                    Toast.makeText(this, "You are on the last step. ", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    /*
    REACT TO NAVIGATION BUTTONS
    NEEDED FOR DUALPANE MODE
     */
    private void showStep(String action){
        Log.d(TAG, "StepDetailActivity.showStep: action="+action);
        //MEDIA PLAYER
        String videoURL = RecipeJSON.getCurrRecipeStepVideoURL(RecipeJSON.getCurrentRecipeStepNum());

        switch(action){
            case "new":
                stepNavigationFragment = new StepNavigationFragment();

                //Determine if we should show Ingredients or a recipe step
                if (RecipeJSON.isShowingIngredients()){
                    //show ingredients fragment in place of Video+instructions
                    ingredientsFrame.setVisibility(View.VISIBLE);
                    //Hide mediaplayerfragment
                    mediaPlayerFrame.setVisibility(View.GONE);
                    //Hide stepinstructionsfragment
                    recipeInstructionsFrame.setVisibility(View.GONE);
                    stepIngredientsFragment = new IngredientListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.ingredients_container, stepIngredientsFragment,getString(R.string.TAG_FRAGMENT_INGREDIENTS))
                            .commit();

                } else {
                    //show video + instructions
                    ingredientsFrame.setVisibility(View.GONE);
                    //Hide mediaplayerfragment
                    mediaPlayerFrame.setVisibility(View.VISIBLE);
                    //Hide stepinstructionsfragment
                    recipeInstructionsFrame.setVisibility(View.VISIBLE);

                    playerFragment = new MediaPlayerFragment();
                    stepInstructionsFragment = new StepInstructionsFragment();


                    playerFragment.setVideoUri(videoURL);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.mediaplayer_container, playerFragment,getString(R.string.TAG_FRAGMENT_MEDIAPLAY))
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.instructions_container, stepInstructionsFragment,getString(R.string.TAG_FRAGMENT_INSTRUCTIONS))
                            .commit();

                }
                //Always show navigation
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navigation_container, stepNavigationFragment,getString(R.string.TAG_FRAGMENT_NAVIGATION))
                        .commit();

                break;
            case "replace":
                //Determine if we should show Ingredients or a recipe step
                stepNavigationFragment = new StepNavigationFragment();
                if (RecipeJSON.isShowingIngredients()) {
                    //show ingredients fragment in place of Video+instructions
                    ingredientsFrame.setVisibility(View.VISIBLE);
                    //Hide mediaplayerfragment
                    mediaPlayerFrame.setVisibility(View.GONE);
                    //Hide stepinstructionsfragment
                    recipeInstructionsFrame.setVisibility(View.GONE);
                    stepIngredientsFragment = new IngredientListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ingredients_container, stepIngredientsFragment,getString(R.string.TAG_FRAGMENT_INGREDIENTS))
                            .commit();
                } else {
                    //show video + instructions
                    ingredientsFrame.setVisibility(View.GONE);
                    //Hide mediaplayerfragment
                    mediaPlayerFrame.setVisibility(View.VISIBLE);
                    //Hide stepinstructionsfragment
                    recipeInstructionsFrame.setVisibility(View.VISIBLE);

                    playerFragment = new MediaPlayerFragment();
                    stepInstructionsFragment = new StepInstructionsFragment();
                    playerFragment.setVideoUri(videoURL);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.mediaplayer_container, playerFragment,getString(R.string.TAG_FRAGMENT_MEDIAPLAY))
                            .commit();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.instructions_container, stepInstructionsFragment,getString(R.string.TAG_FRAGMENT_INSTRUCTIONS))
                            .commit();

                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.navigation_container, stepNavigationFragment,getString(R.string.TAG_FRAGMENT_NAVIGATION))
                        .commit();
                break;
            case "restore":
                playerFragment = (MediaPlayerFragment) getSupportFragmentManager()
                        .findFragmentByTag(getString(R.string.TAG_FRAGMENT_MEDIAPLAY));
                stepInstructionsFragment = (StepInstructionsFragment) getSupportFragmentManager()
                        .findFragmentByTag(getString(R.string.TAG_FRAGMENT_INSTRUCTIONS));
                stepNavigationFragment = (StepNavigationFragment) getSupportFragmentManager()
                        .findFragmentByTag(getString(R.string.TAG_FRAGMENT_NAVIGATION));
                stepIngredientsFragment = (IngredientListFragment) getSupportFragmentManager()
                        .findFragmentByTag(getString(R.string.TAG_FRAGMENT_INGREDIENTS));

                break;
        }

    }
}
