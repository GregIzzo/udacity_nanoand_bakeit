package com.example.android.udacity_nanoand_bakeit;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class BakeItRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
    private Context context = null;
    private int appWdgetId;


    public BakeItRemoteViewsFactory(Context applicationContext){
        context = applicationContext;
    }
/*
    public BakeItRemoteViewsFactory(Context applicationContext, Intent intent) {
        Log.d(TAG, "BakeItRemoteViewsFactory: $$$$$ $$$$$$");
        this.context = context;
        appWdgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

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
*/
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        //reset my data
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
        Log.d(TAG, "getViewAt: $$$$$$$$$$$ pos="+pos);
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_row);
        ListItem listItem = listItemList.get(pos);
        remoteView.setTextViewText(R.id.tv_ingredient, listItem.ingredient);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
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
