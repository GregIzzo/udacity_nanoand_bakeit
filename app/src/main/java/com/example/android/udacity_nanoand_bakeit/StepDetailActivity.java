package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

public class StepDetailActivity extends AppCompatActivity {
    private String TAG  = "GGG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "StepDetailActivity.onCreate: STARTING ");
        setContentView(R.layout.activity_step_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
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
            //MEDIA PLAYER

            String videoURL = RecipeJSON.getCurrRecipeStepVideoURL(RecipeJSON.getCurrentRecipeStepNum());

            if (videoURL.length() == 0){
                //No Video
                Log.d(TAG, "onCreate: @@@@@ THIS STEP HAS NO VIDEO @@@@@@@@@");
            } else {
                MediaPlayerFragment playerFragment = new MediaPlayerFragment();
                playerFragment.setVideoUri(Uri.parse(videoURL));
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.mediaplayer_container, playerFragment)
                        .commit();

                Log.d(TAG, "onCreate: @@@@@ THIS STEP HAS VIDEO ["+videoURL+"]@@@@@@@@@");
               // playerFragment.initializePlayer(Uri.parse(videoURL), this);
            }

            //Step instructions /////////////////////////////////////////////////
            StepInstructionsFragment stepInstructionsFragment = new StepInstructionsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.instructions_container, stepInstructionsFragment)
                    .commit();
            //Step Navigation /////////////////////////////////////////////////
            StepNavigationFragment stepNavigationFragment = new StepNavigationFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.navigation_container, stepNavigationFragment)
                    .commit();



            /*
            StepInstructionsFragment fragment = new StepInstructionsFragment();
            // fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, fragment)
                    .commit();
                    */

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

}
