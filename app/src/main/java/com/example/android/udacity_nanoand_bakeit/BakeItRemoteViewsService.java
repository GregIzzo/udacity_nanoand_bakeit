package com.example.android.udacity_nanoand_bakeit;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

public class BakeItRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "BakeItRemoteViewsService.onGetViewFactory: $$$$$$$$$");
    //    int appWidgetId = intent.getIntExtra(
    //            AppWidgetManager.EXTRA_APPWIDGET_ID,
     //           AppWidgetManager.INVALID_APPWIDGET_ID);

        return new BakeItRemoteViewsFactory(this.getApplicationContext(),intent);

    }

}
