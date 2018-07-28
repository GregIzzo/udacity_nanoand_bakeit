package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;


import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;
import com.example.android.udacity_nanoand_bakeit.utilities.NetworkUtils;

import java.net.URL;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeStepsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipeListRecyclerAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG ="GREG_RECIPELISTACTIVITY" ;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    //private boolean mTwoPane;
    private static final int MOVIE_LOADER_ID= 24;
    private String mRecipeData = null;

    private RecyclerView recyclerView;
    private RecipeListRecyclerAdapter recipeRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
/*
        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
*/
        recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;

       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recipeRecyclerAdapter  = new RecipeListRecyclerAdapter(this);
        /* Setting the adapter attaches it to the RecyclerView in our layout. */


        //Wait until data is loaded before setting up recycler view
       // setupRecyclerView((RecyclerView) recyclerView);
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, MainActivity.this );

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
       // recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this,  mTwoPane));
        Log.d(TAG, "setupRecyclerView: *** Setting Adapter");
        recyclerView.setAdapter(recipeRecyclerAdapter);

        recipeRecyclerAdapter.setRecipeData("123");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader:********** starting ");
        return  new  AsyncTaskLoader<String>(this ) {

            @Override
            public String loadInBackground() {
                URL searchUrl;
                try {
                    searchUrl = NetworkUtils.buildDataURL();
                    Log.d(TAG, "loadInBackground: *** URL["+searchUrl.toString()+"]");
                    return NetworkUtils
                            .getResponseFromHttpUrl(searchUrl);
                } catch (Exception e) {
                    Log.d(TAG, "loadInBackground:CATCH ERROR  ");
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onStartLoading() {
                Log.d(TAG, "onStartLoading: ***** onStartLoading ******");
                if (mRecipeData != null) {
                    Log.d(TAG, "onStartLoading: mRecipeData NOT null:" + mRecipeData.length());
                    deliverResult(mRecipeData);
                } else {
                    //loadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }
            public void deliverResult(String data) {
                if (data == null) {
                    //   Log.i("GREGOUT", "#####  deliverResult #####: data.length=0 (null) " );
                } else {
                    Log.d("GREGOUT", "#####  deliverResult #####: data.length " + data.length());
                    mRecipeData = data;
                    //Turn it to a json object:

                }
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (s == null){
            Log.d(TAG, "onLoadFinished: data string is null:" );
        }else {
            Log.d(TAG, "onLoadFinished: data string len is:" + s.length());
        }
        //Got data - so setup recycler view
        RecipeJSON.setDataString(s);
        setupRecyclerView( recyclerView);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onClick(int listPosition) {
        Log.d(TAG, "MainActivity onClick: pos=" + listPosition);
        /*
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);
            RecipeStepsFragment fragment = new RecipeStepsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        } else {
*/
        RecipeJSON.setCurrentRecipe(listPosition);
            Intent intent = new Intent(this, RecipeStepsActivity.class);
        //    intent.putExtra(RecipeStepsFragment.ARG_RECIPE_INDEX, listPosition);

            startActivity(intent);
/*
        }
        */

    }
}
