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

import org.apache.commons.lang3.StringUtils;


/**
 * Created by Greg on 5/15/2018.
 */
public class RecipeStepsRecyclerAdapter extends RecyclerView.Adapter<RecipeStepsRecyclerAdapter.RecipeStepsAdapterViewHolder> {

    private static final String TAG = RecipeStepsRecyclerAdapter.class.getSimpleName();
    //private int mNumberOfItems;
    private String recipeJsonData;
    private final RecipeStepsAdapterOnClickHandler mClickHandler;
    private Context viewGroupContext;

    // private JSONObject reader = null;
    // private  JSONArray resArray=null;


    public interface RecipeStepsAdapterOnClickHandler {
        void onClick(int listPosition);// throws JSONException;
    }
    /*
     * Constructor for RecipeStepsRecyclerAdapter - accepts number of items to display
     */
    public RecipeStepsRecyclerAdapter(RecipeStepsAdapterOnClickHandler mclick) {
        // mNumberOfItems = numberOfItems;
        mClickHandler = mclick;
    }


    public class RecipeStepsAdapterViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

       // public final ImageView listItemRecipeImageView;
      //  public final TextView listItemRecipeName;

        public final TextView stepId;
        public final TextView stepShortDesc;
        //public final TextView stepLongDesc;
        public final ImageView stepVideoURL;
        public final ImageView stepImageURL;


        public RecipeStepsAdapterViewHolder( View itemView) {
            super(itemView);
           // listItemRecipeImageView =  itemView.findViewById(R.id.iv_recipe_image);
           // listItemRecipeName = itemView.findViewById(R.id.recipe_name);

            stepId = itemView.findViewById(R.id.tv_recipe_id);
            stepShortDesc = itemView.findViewById(R.id.tv_recipe_shortdescription);
           // stepLongDesc = itemView.findViewById(R.id.tv_recipe_description);

            stepVideoURL = itemView.findViewById(R.id.tv_recipe_videourl);
            stepImageURL = itemView.findViewById(R.id.tv_recipe_imageurl);


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
    public RecipeStepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        viewGroupContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipesteps_detail;// R.layout.recipes_list_card;
        LayoutInflater inflater = LayoutInflater.from(viewGroupContext);
// View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.recipes_list_card, parent, false);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new RecipeStepsAdapterViewHolder(view);
    }
    /**
     * OnBindViewHolder is called by RecyclerView to display the data at the specified
     * position. In this method, update the contents of the ViewHolder to display the movie
     * image for the given position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param RecipeStepsAdapterViewHolder The RecipeStepsAdapterViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeStepsAdapterViewHolder RecipeStepsAdapterViewHolder, int position) {
        //set the image of the ImageView

        // if (recipeJsonData == null){
        //     return;
        //  }
        //   try {
        // JSONObject jObj = resArray.getJSONObject(position);
        // JSONObject jsonObject = RecipeJSON.getRecipe(position);
        //String imagePath = RecipeJSON.getRecipeImage(position);

        //String imurl = "https://image.tmdb.org/t/p/w500" + posterPath;
      //  RecipeStepsAdapterViewHolder.listItemRecipeName.setText(RecipeJSON.getRecipeName(position));
    if (position == 0) {
        //Position 0 is the 'Ingredients' step
      // RecipeStepsAdapterViewHolder.stepId.setText("");
        RecipeStepsAdapterViewHolder.stepShortDesc.setText(R.string.ingredient_step);
       // RecipeStepsAdapterViewHolder.stepLongDesc.setText("");
        RecipeStepsAdapterViewHolder.stepVideoURL.setVisibility(View.GONE);
      //  RecipeStepsAdapterViewHolder.stepVideoURL.setText("");
        RecipeStepsAdapterViewHolder.stepImageURL.setVisibility(View.GONE);
       // RecipeStepsAdapterViewHolder.stepImageURL.setText("");
    } else {
        int step = position -1;
        RecipeStepsAdapterViewHolder.stepId.setText("" + (RecipeJSON.getCurrRecipeStepId(step)));
        RecipeStepsAdapterViewHolder.stepShortDesc.setText(RecipeJSON.getCurrRecipeStepShortDescription(step));
       // RecipeStepsAdapterViewHolder.stepLongDesc.setText(RecipeJSON.getCurrRecipeStepDescription(step));
        Log.d(TAG, "onBindViewHolder: ******** videurl["+RecipeJSON.getCurrRecipeStepVideoURL(step)+" length="+RecipeJSON.getCurrRecipeStepVideoURL(step).length()+"] imageurl["+RecipeJSON.getCurrRecipeStepThumbnailURL(step)+" length="+RecipeJSON.getCurrRecipeStepThumbnailURL(step).length()+"]");
        if (StringUtils.isEmpty(RecipeJSON.getCurrRecipeStepVideoURL(step))) {
            RecipeStepsAdapterViewHolder.stepVideoURL.setVisibility(View.GONE);
            Log.d(TAG, "onBindViewHolder: ^^^^^^ Hiding Video icon");
        }
       // RecipeStepsAdapterViewHolder.stepVideoURL.setText(RecipeJSON.getCurrRecipeStepVideoURL(step));
        if (StringUtils.isEmpty(RecipeJSON.getCurrRecipeStepThumbnailURL(step))) {
            RecipeStepsAdapterViewHolder.stepImageURL.setVisibility(View.GONE);
            Log.d(TAG, "onBindViewHolder: ^^^^^^ Hiding Image icon");
        }
       // RecipeStepsAdapterViewHolder.stepImageURL.setText(RecipeJSON.getCurrRecipeStepThumbnailURL(step));
        Log.d(TAG, "onBindViewHolder: step:"+step+" thumnail["+RecipeJSON.getCurrRecipeStepThumbnailURL(step)+"]");
    }
        /*
        if (imagePath != null && imagePath.length() > 0) {
            Log.d("ZZZZ", "onBindViewHolder: ABOUT TO RUN PICASSO. impagePath.length="+ imagePath.length());
            Picasso.get()
                    .load(imagePath)
                    .placeholder(R.mipmap.recipe_200x200)
                    .error(R.mipmap.recipe_200x200)
                    .into(RecipeStepsAdapterViewHolder.listItemRecipeImageView);
        }
        */


        // holder.mContentView.setText(RecipeJSON.getRecipeName(position));

        //holder.itemView.setTag(mValues.get(position));
        ////// RecipeStepsAdapterViewHolder.itemView.setOnClickListener(mClickHandler);
        //} catch (JSONException e) {
        //     e.printStackTrace();
        //  }
    }


    @Override
    public int getItemCount() {
        return RecipeJSON.getCurrRecipeStepCount() + 1; //Adding 1 to allow 'ingredients' to appear in first position

    }

    /*

     */
    public void setRecipeData(String recipeData) {
        recipeJsonData = recipeData;

        notifyDataSetChanged();
    }

}
