package com.udacity.stockhawk;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.ui.MainActivity;
import com.udacity.stockhawk.ui.StockHistoryActivity;

import java.util.ArrayList;
import java.util.List;

public class StockWidgetProvider extends AppWidgetProvider {
    private static final String STOCK_ACTION = "com.udacity.stockhawk.STOCK_ACTION";
    public static final String STOCK_SYMBOL = "com.udacity.stockhawk.STOCK_SYMBOL";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int widgetCount = appWidgetIds.length;

        for (int i = 0; i < widgetCount; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, StockWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.stock_widget);
            rv.setRemoteAdapter(appWidgetId, R.id.widget_list, intent);

            Intent mainActivityIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, 0);
            rv.setOnClickPendingIntent(R.id.widget_layout_main, pendingIntent);

            Intent stockIntent = new Intent(context, StockHistoryActivity.class);
            PendingIntent stockPendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(stockIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.widget_list, stockPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
