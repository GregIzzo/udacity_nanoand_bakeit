package com.example.android.udacity_nanoand_bakeit;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.udacity_nanoand_bakeit.data.PrefHandler;
import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class BakeItRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();

    private Context context = null;
    private int appWdgetId;

/*
    public BakeItRemoteViewsFactory(Context applicationContext){
        Log.d(TAG, "BakeItRemoteViewsFactory.BakeItRemoteViewsFactory: ");
        context = applicationContext;
    }
    */


    public BakeItRemoteViewsFactory(Context applicationContext, Intent intent) {
        Log.d(TAG, "BakeItRemoteViewsFactory: $$$$$ $$$$$$");
        context = applicationContext;
        appWdgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem(){
        Log.d(TAG, "populateListItem: $$$$$ $$$$$$");
        for (int i=0; i<10; i++){
            ListItem listItem = new ListItem();
            listItem.ingredient = "Ingredient:"+i;
            listItemList.add(listItem);
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "BakeItRemoteViewsFactory.onCreate: $$");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "BakeItRemoteViewsFactory.onDataSetChanged: $$");

        //reset my data
        // Get currentRecipe from prefs
        JSONObject currRecipe = PrefHandler.getCurrentRecipe(context);
        RecipeJSON.setCurrentRecipe(currRecipe);
        ///TEMPORARY//////////////////////////////////////////////
        if (RecipeJSON.getCurrentRecipe() == null){
            RecipeJSON.setCurrentRecipe(1);
        }
        listItemList = new ArrayList<ListItem>();
        for (int i=0; i<RecipeJSON.getCurrRecipeIngredientsCount(); i++){
            ListItem listItem = new ListItem();
            listItem.ingredient = RecipeJSON.getCurrRecipeIngredientCompleteString(i);
            listItemList.add(listItem);
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
       return listItemList.size();
    }

    @Override
    public RemoteViews getViewAt(int pos) {
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_row);
        ListItem listItem = listItemList.get(pos);
        remoteView.setTextViewText(R.id.tv_ingredient, listItem.ingredient);
        Log.d(TAG, "getViewAt: $$$$$$$$$$$ pos="+pos+" ing="+listItem.ingredient);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
