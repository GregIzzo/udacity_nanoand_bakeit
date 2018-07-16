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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Greg on 5/15/2018.
 */
public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeAdapterViewHolder> {

    //private static final String TAG = RecipeRecyclerAdapter.class.getSimpleName();
    //private int mNumberOfItems;
    private String recipeJsonData;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private Context viewGroupContext;

    // private JSONObject reader = null;
   // private  JSONArray resArray=null;


    public interface RecipeAdapterOnClickHandler {
        void onClick(int listPosition);// throws JSONException;
    }
    /*
     * Constructor for RecipeRecyclerAdapter - accepts number of items to display
     */
    public RecipeRecyclerAdapter(RecipeAdapterOnClickHandler mclick) {
        // mNumberOfItems = numberOfItems;
        mClickHandler = mclick;
    }


    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public final ImageView listItemRecipeImageView;
        public final TextView listItemRecipeName;

        public RecipeAdapterViewHolder( View itemView) {
            super(itemView);
            listItemRecipeImageView =  itemView.findViewById(R.id.iv_recipe_image);
            listItemRecipeName = itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            //Just send position -
            mClickHandler.onClick(adapterPosition);

            /*
            JSONObject jObj = null;

            if (resArray != null){
                try {
                    jObj = resArray.getJSONObject(adapterPosition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (jObj != null){
                try {
                    mClickHandler.onClick(jObj.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            */

        }
    }


    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        viewGroupContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_content;
        LayoutInflater inflater = LayoutInflater.from(viewGroupContext);
// View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.recipe_list_content, parent, false);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new RecipeAdapterViewHolder(view);
    }
    /**
     * OnBindViewHolder is called by RecyclerView to display the data at the specified
     * position. In this method, update the contents of the ViewHolder to display the movie
     * image for the given position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param RecipeAdapterViewHolder The RecipeAdapterViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder RecipeAdapterViewHolder, int position) {
        //set the image of the ImageView

       // if (recipeJsonData == null){
       //     return;
      //  }
     //   try {
           // JSONObject jObj = resArray.getJSONObject(position);
           // JSONObject jsonObject = RecipeJSON.getRecipe(position);
            String imagePath = RecipeJSON.getRecipeImage(position);

            //String imurl = "https://image.tmdb.org/t/p/w500" + posterPath;
       RecipeAdapterViewHolder.listItemRecipeName.setText(RecipeJSON.getRecipeName(position));
        Log.d("ZZZZ", "onBindViewHolder: imagePath=["+imagePath+"]");

        if (imagePath != null && imagePath.length() > 0) {
            Log.d("ZZZZ", "onBindViewHolder: ABOUT TO RUN PICASSO. impagePath.length="+ imagePath.length());
                Picasso.get()
                        .load(imagePath)
                        .placeholder(R.mipmap.recipe_200x200)
                        .error(R.mipmap.recipe_200x200)
                        .into(RecipeAdapterViewHolder.listItemRecipeImageView);
            }
             // holder.mContentView.setText(RecipeJSON.getRecipeName(position));

            //holder.itemView.setTag(mValues.get(position));
           ////// RecipeAdapterViewHolder.itemView.setOnClickListener(mClickHandler);
        //} catch (JSONException e) {
       //     e.printStackTrace();
      //  }
    }


    @Override
    public int getItemCount() {
        return RecipeJSON.size();

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
            //   Log.i(TAG, "**** RecipeRecyclerAdapter.setRecipeData: str len= 0 calling notifyDataSetChanged");
        } else {
            try {
                resArray = new JSONArray(recipeJsonData);
                //resArray = reader.getJSONArray("results");
            } catch (JSONException e) {
                //   Log.i(TAG, "^^^^^^^^^ ERROR onBindViewHolder - creating JSONObject");
                e.printStackTrace();
            }
            // Log.i(TAG, "**** RecipeRecyclerAdapter.setRecipeData: str len= "+recipeData.length() + " calling notifyDataSetChanged");
        }
*/

        notifyDataSetChanged();
    }

}
