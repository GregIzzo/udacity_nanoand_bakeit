package com.example.android.bakeit;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.udacity_nanoand_bakeit.MainActivity;
import com.example.android.udacity_nanoand_bakeit.R;
import com.example.android.udacity_nanoand_bakeit.BakeItRemoteViewsService;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

/**
 * Implementation of App Widget functionality.
 */
public class BakeItWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "updateAppWidget: $$ $$ $$");
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_it_widget);
        views.setOnClickPendingIntent(R.id.tv_title, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            Log.d(TAG, "BakeItWidgetProvider.onUpdate: $$$$$$$$  appWidgetIds.length="+ appWidgetIds.length);
            final int N = appWidgetIds.length;
//FROM: https://developer.android.com/guide/topics/appwidgets
            ///TEMPORARY FOR TESTING CLICK FUNCTIONALITY
            /*
            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
*/

            Intent clickintent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickintent, 0);

            for (int i = 0; i < N; ++i) {

                Intent intent = new Intent(context, BakeItRemoteViewsService.class);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

                intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
                RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.bake_it_widget );
                rv.setRemoteAdapter(appWidgetIds[i],  intent);
                rv.setEmptyView(R.id.lv_ingredients, R.id.empty_view);
                rv.setOnClickPendingIntent(R.id.tv_title, pendingIntent);
               // RemoteViews remoteViews = updateWidgetListView(context,appWidgetIds[i]);
                appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
            }
            super.onUpdate(context, appWidgetManager, appWidgetIds);

        }

        private RemoteViews updateWidgetListViewOLD(Context context, int appWidgetId) {
            Log.d(TAG, "BakeItWidgetProvider.updateWidgetListView: $$$$$$$ appWidgetId="+appWidgetId);
            //which layout to show on widget
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.bake_it_widget);

            //RemoteViews Service needed to provide adapter for ListView
            Intent svcIntent = new Intent(context, BakeItRemoteViewsService.class);
            //passing app widget id to that RemoteViews Service
            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            //setting a unique Uri to the intent
            //don't know its purpose to me right now
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
            //setting adapter to listview of the widget
            remoteViews.setRemoteAdapter( R.id.lv_ingredients,svcIntent);
            //    .setRemoteAdapter(appWidgetId, R.id.listViewWidget, svcIntent);

            //setting an empty view in case of no data
            remoteViews.setEmptyView(R.id.lv_ingredients, R.id.empty_view);
            return remoteViews;
        }


}
