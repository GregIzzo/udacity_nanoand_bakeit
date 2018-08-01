package com.example.android.udacity_nanoand_bakeit;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
//import android.util.Log;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;
import com.squareup.picasso.Picasso;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

/**
 * Created by Greg on 5/15/2018.
 */
public class IngredientListRecyclerAdapter extends RecyclerView.Adapter<IngredientListRecyclerAdapter.IngredientListAdapterViewHolder> {

    //private static final String TAG = IngredientListRecyclerAdapter.class.getSimpleName();
    //private int mNumberOfItems;
    private String recipeJsonData;
    private final IngredientListAdapterOnClickHandler mClickHandler;
    private Context viewGroupContext;

    // private JSONObject reader = null;
    // private  JSONArray resArray=null;


    public interface IngredientListAdapterOnClickHandler {
        void onClick(int listPosition);// throws JSONException;
    }
    /*
     * Constructor for IngredientListRecyclerAdapter - accepts number of items to display
     */
    public IngredientListRecyclerAdapter(IngredientListAdapterOnClickHandler mclick) {
        // mNumberOfItems = numberOfItems;
        mClickHandler = mclick;
    }


    public class IngredientListAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {


        public final TextView listItemIngredientQuantity;
        public final TextView listItemIngredientMeasure;
        public final TextView listItemIngredient;

        public IngredientListAdapterViewHolder( View itemView) {
            super(itemView);

            listItemIngredientQuantity = itemView.findViewById(R.id.tv_quantity);
            listItemIngredientMeasure = itemView.findViewById(R.id.tv_measure);
            listItemIngredient = itemView.findViewById(R.id.tv_ingredient);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //Just send position -
            mClickHandler.onClick(adapterPosition);
        }
    }


    @NonNull
    @Override
    public IngredientListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        viewGroupContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.ingredient_view;
        LayoutInflater inflater = LayoutInflater.from(viewGroupContext);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new IngredientListAdapterViewHolder(view);
    }
    /**
     * OnBindViewHolder is called by RecyclerView to display the data at the specified
     * position. In this method, update the contents of the ViewHolder to display the movie
     * image for the given position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param IngredientListAdapterViewHolder The IngredientListAdapterViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull IngredientListAdapterViewHolder IngredientListAdapterViewHolder, int position) {
        //Ingredient data:
        // "quantity" :int
        // "measure" : String
        // "ingredient":String

        String imagePath = RecipeJSON.getRecipeImage(position);
        Log.d(TAG, "GGGGGGG onBindViewHolder: position="+position);
        //String imurl = "https://image.tmdb.org/t/p/w500" + posterPath;
        int quant =RecipeJSON.getCurrRecipeIngredientInt(position, "quantity");
        IngredientListAdapterViewHolder.listItemIngredientQuantity.setText("" + quant);
        IngredientListAdapterViewHolder.listItemIngredientMeasure.setText(RecipeJSON.getCurrRecipeIngredientString(position, "measure"));
        IngredientListAdapterViewHolder.listItemIngredient.setText(RecipeJSON.getCurrRecipeIngredientString(position, "ingredient"));
/*
        if (imagePath != null && imagePath.length() > 0) {
            Log.d("ZZZZ", "onBindViewHolder: ABOUT TO RUN PICASSO. impagePath.length="+ imagePath.length());
            Picasso.get()
                    .load(imagePath)
                    .placeholder(R.mipmap.recipe_200x200)
                    .error(R.mipmap.recipe_200x200)
                    .into(IngredientListAdapterViewHolder.listItemRecipeImageView);
        }
*/
        // holder.mContentView.setText(RecipeJSON.getRecipeName(position));

        //holder.itemView.setTag(mValues.get(position));
        ////// IngredientListAdapterViewHolder.itemView.setOnClickListener(mClickHandler);
        //} catch (JSONException e) {
        //     e.printStackTrace();
        //  }
    }


    @Override
    public int getItemCount() {
        return RecipeJSON.getCurrRecipeIngredientsCount();

        /*
        if (null == resArray){
            //  Log.i(TAG, " ** ** ** getItemCountbn null: 0");
            return 0;
        }
        //   Log.i(TAG, " ** ** ** getItemCount: "+resArray.length());
        return resArray.length();
        */
    }

    /*

     */
    public void setRecipeData(String recipeData) {
        recipeJsonData = recipeData;
 /*
        if (recipeData == null){
            // reader = null;
            resArray = null;
            //   Log.i(TAG, "**** IngredientListRecyclerAdapter.setRecipeData: str len= 0 calling notifyDataSetChanged");
        } else {
            try {
                resArray = new JSONArray(recipeJsonData);
                //resArray = reader.getJSONArray("results");
            } catch (JSONException e) {
                //   Log.i(TAG, "^^^^^^^^^ ERROR onBindViewHolder - creating JSONObject");
                e.printStackTrace();
            }
            // Log.i(TAG, "**** IngredientListRecyclerAdapter.setRecipeData: str len= "+recipeData.length() + " calling notifyDataSetChanged");
        }
*/

        notifyDataSetChanged();
    }

}
