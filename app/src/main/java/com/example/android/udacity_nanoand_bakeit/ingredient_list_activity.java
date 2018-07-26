package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

public class ingredient_list_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);
        LinearLayout mLayout=(LinearLayout)findViewById(R.id.list_container);


        if (savedInstanceState == null) {
           //Add item for each ingredient
            int ingCount = RecipeJSON.getCurrRecipeIngredientsCount();
            Log.d("GGG", "ingredient_list_activity.onCreate ingredient count= " + ingCount);

            for (int i=0; i<ingCount; i++){
                View view = LayoutInflater.from(this).inflate(R.layout.ingredient_view, null);

                TextView tvQuantity = (TextView) view.findViewById(R.id.tv_quantity);
                TextView tvMeasure = (TextView) view.findViewById(R.id.tv_measure);
                TextView tvIngredient = (TextView) view.findViewById(R.id.tv_ingredient);
                Log.d("GGG", "ingredient_list_activity.onCreate: tvQuantity="+tvQuantity+" tvmeasure="+tvMeasure+" tvIngredient="+tvIngredient);
                tvQuantity.setText("" + RecipeJSON.getCurrRecipeIngredientInt(i,"quantity"));
                tvMeasure.setText(RecipeJSON.getCurrRecipeIngredientString(i,"measure"));
                tvIngredient.setText(RecipeJSON.getCurrRecipeIngredientString(i,"ingredient"));

                mLayout.addView(view);

            }

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
