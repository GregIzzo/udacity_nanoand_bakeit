package com.example.android.udacity_nanoand_bakeit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;
import com.example.android.udacity_nanoand_bakeit.dummy.DummyContent;
import com.example.android.udacity_nanoand_bakeit.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.util.List;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipeRecyclerAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG ="GREG_RECIPELISTACTIVITY" ;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final int MOVIE_LOADER_ID= 24;
    private String mRecipeData = null;

    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

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

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;

       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recipeRecyclerAdapter  = new RecipeRecyclerAdapter(this);
        /* Setting the adapter attaches it to the RecyclerView in our layout. */


        //Wait until data is loaded before setting up recycler view
       // setupRecyclerView((RecyclerView) recyclerView);
        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, null, RecipeListActivity.this );

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
        Log.d(TAG, "onLoadFinished: data string is:" + s);
        //Got data - so setup recycler view
        RecipeJSON.setDataString(s);
        setupRecyclerView( recyclerView);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onClick(int listPosition) {
        Log.d(TAG, "RecipeListActivity onClick: pos=" + listPosition);
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putInt(RecipeDetailFragment.ARG_ITEM_ID, listPosition);
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        } else {

            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, listPosition);

            startActivity(intent);
        }

    }
/*
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
       // private final RecipeJSON mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, item.id);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      boolean twoPane) {
            //PREVIOUSLY, the data was passed in as 2nd param. Now it's being read from a static class
           // mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(RecipeJSON.getRecipeName(position));
           // holder.mContentView.setText(RecipeJSON.getRecipeName(position));

            //holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return RecipeJSON.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
           // final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.recipe_name);
               // mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }

    */
}
