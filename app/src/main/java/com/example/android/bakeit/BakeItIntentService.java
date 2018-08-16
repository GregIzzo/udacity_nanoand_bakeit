package com.example.android.bakeit;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.udacity_nanoand_bakeit.R;
import com.example.android.udacity_nanoand_bakeit.data.PrefHandler;
import com.example.android.udacity_nanoand_bakeit.data.RecipeJSON;

import static android.support.constraint.Constraints.TAG;

public class BakeItIntentService extends IntentService {
    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.android.bakeit.action.update_ingredients";

    public BakeItIntentService(String name) {
        super(name);
    }
    public BakeItIntentService() {
        super("BakeItIntentService");
    }
    public static void startActionUpdateIngredients(Context context) {
        Log.d(TAG, "BAkeItIntentService.startActionUpdateIngredients: $$$ curre recipe="+ RecipeJSON.getCurrRecipeName()+" context="+context.toString());
        Intent intent = new Intent(context, BakeItIntentService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "BAkeItIntentService.onHandleIntent: Action="+action+": $$$");
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {

                //final long plantId = intent.getLongExtra(EXTRA_PLANT_ID, PlantContract.INVALID_PLANT_ID);
                //handleActionWaterPlant(plantId);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeItWidgetProvider.class));
                //Trigger data update to handle the GridView widgets and force a data refresh
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients);
BakeItWidgetProvider.updateBakeItWidgets(this, appWidgetManager,appWidgetIds );

            }
        }

    }
}
