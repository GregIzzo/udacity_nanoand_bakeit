package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

import butterknife.BindView;

public class IngredientListActivity extends AppCompatActivity implements IngredientListRecyclerAdapter.IngredientListAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private IngredientListRecyclerAdapter ingredientListRecyclerAdapter;
    //Toolbar toolbar;
   // FloatingActionButton fab;
   // RecyclerView recyclerView;

   // @BindView(R.id.detail_toolbar) Toolbar toolbar;
   // @BindView(R.id.fab) FloatingActionButton fab;
   // @BindView(R.id.list_container) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
       Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        toolbar.setTitle(RecipeJSON.getCurrRecipeName() + " : " + getString(R.string.ingredient_step) );
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
            recyclerView = findViewById(R.id.list_container);
            assert recyclerView != null;

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            if (mLayoutManager == null) Log.d("GGG", "onCreate: LAYOUTMANAGER IS NULL");
            recyclerView.setLayoutManager(mLayoutManager);
            ingredientListRecyclerAdapter = new IngredientListRecyclerAdapter(this);
            recyclerView.setAdapter(ingredientListRecyclerAdapter);
            ingredientListRecyclerAdapter.setRecipeData("123");

/*
           //Add item for each ingredient
            int ingCount = RecipeJSON.getCurrRecipeIngredientsCount();
            Log.d("GGG", "IngredientListActivity.onCreate ingredient count= " + ingCount);
            for (int i=0; i<ingCount; i++){
                View view = LayoutInflater.from(this).inflate(R.layout.ingredient_view, null);
                TextView tvQuantity = (TextView) view.findViewById(R.id.tv_quantity);
                TextView tvMeasure = (TextView) view.findViewById(R.id.tv_measure);
                TextView tvIngredient = (TextView) view.findViewById(R.id.tv_ingredient);
                Log.d("GGG", "IngredientListActivity.onCreate: tvQuantity="+tvQuantity+" tvmeasure="+tvMeasure+" tvIngredient="+tvIngredient);
                tvQuantity.setText("" + RecipeJSON.getCurrRecipeIngredientInt(i,"quantity"));
                tvMeasure.setText(RecipeJSON.getCurrRecipeIngredientString(i,"measure"));
                tvIngredient.setText(RecipeJSON.getCurrRecipeIngredientString(i,"ingredient"));
                mLayout.addView(view);
            }
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

    @Override
    public void onClick(int listPosition) {

    }
}
