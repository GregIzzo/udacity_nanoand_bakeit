package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

public class StepDetailActivity extends AppCompatActivity
        implements StepNavigationFragment.OnNavClickListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    private String TAG  = "GGG";
    private GestureDetectorCompat mGestureDetector;
    MediaPlayerFragment playerFragment;
    StepInstructionsFragment stepInstructionsFragment;
    StepNavigationFragment stepNavigationFragment;

    private String TAG_FRAGMENT_MEDIAPLAY = "step_media_player";
    private String TAG_FRAGMENT_INSTRUCTIONS = "step_instructions";
    private String TAG_FRAGMENT_NAVIGATION = "step_navigation";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "StepDetailActivity.onCreate: STARTING ");
        setContentView(R.layout.activity_step_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(RecipeJSON.getCurrRecipeName());
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
        if (savedInstanceState == null) {
            showStep("new");
        } else {
            showStep("restore");
        }
        mGestureDetector = new GestureDetectorCompat(this, this);
        mGestureDetector.setOnDoubleTapListener(this);
    }
    /*
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.d(TAG, "onTouchEvent: EVENT="+event.toString());
            return super.onTouchEvent(event);
        }
    */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mGestureDetector.onTouchEvent(event)) {
            Log.d(TAG, "onTouchEvent: !!!!" );
            return true;
        }
        return super.onTouchEvent(event);
    }
    private void showStep(String action){
        Log.d(TAG, "StepDetailActivity.showStep: action="+action);
        //MEDIA PLAYER
        String videoURL = RecipeJSON.getCurrRecipeStepVideoURL(RecipeJSON.getCurrentRecipeStepNum());

        switch(action){
            case "new":
                playerFragment = new MediaPlayerFragment();
                stepInstructionsFragment = new StepInstructionsFragment();
                stepNavigationFragment = new StepNavigationFragment();
                playerFragment.setVideoUri(videoURL);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.mediaplayer_container, playerFragment,TAG_FRAGMENT_MEDIAPLAY)
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.instructions_container, stepInstructionsFragment,TAG_FRAGMENT_INSTRUCTIONS)
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.navigation_container, stepNavigationFragment,TAG_FRAGMENT_NAVIGATION)
                        .commit();
                break;
            case "replace":
                playerFragment = new MediaPlayerFragment();
                stepInstructionsFragment = new StepInstructionsFragment();
                stepNavigationFragment = new StepNavigationFragment();
                playerFragment.setVideoUri(videoURL);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mediaplayer_container, playerFragment,TAG_FRAGMENT_MEDIAPLAY)
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.instructions_container, stepInstructionsFragment,TAG_FRAGMENT_INSTRUCTIONS)
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.navigation_container, stepNavigationFragment,TAG_FRAGMENT_NAVIGATION)
                        .commit();
                 break;
            case "restore":
                playerFragment = (MediaPlayerFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_FRAGMENT_MEDIAPLAY);
                stepInstructionsFragment = (StepInstructionsFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_FRAGMENT_INSTRUCTIONS);
                stepNavigationFragment = (StepNavigationFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_FRAGMENT_NAVIGATION);
                break;
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
            navigateUpTo(new Intent(this, RecipeStepsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    Event listener /interface - called when a nav button in StepNavigationFragment is clicked
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
                    Intent intent = new Intent(this, IngredientListActivity.class);
                    startActivity(intent);
                }
                break;
            case "next":
                int currstep = RecipeJSON.getCurrentRecipeStepNum();
                if (currstep < RecipeJSON.getCurrRecipeStepCount() - 1){
                    //go to next step
                    RecipeJSON.setCurrentRecipeStep(RecipeJSON.getCurrentRecipeStepNum() + 1);
                    showStep("replace");
                } else {
                    //Cant go further. Do nothing
                    Toast.makeText(this, "You are on the last step. ", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG, "onDown: !!!!");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.d(TAG, "onShowPress: !!!!");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: !!!!!");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onScroll: !!!");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        int SWIPE_THRESHOLD = 100;
        int SWIPE_VELOCITY_THRESHOLD = 100;

        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        //onSwipeRight();
                        Log.d(TAG, "onFling: SWIPE RIGHT");
                        onNavButtonClicked("prev");
                    } else {
                        //onSwipeLeft();
                        Log.d(TAG, "onFling: SWIPE LEFT");
                        onNavButtonClicked("next");
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    //onSwipeBottom();
                    Log.d(TAG, "onFling: SWIPE DOWN");
                } else {
                    // onSwipeTop();
                    Log.d(TAG, "onFling: SWIPE UP");
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapConfirmed: !!!!");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: !!!");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTapEvent: !!!");
        return false;
    }
}
