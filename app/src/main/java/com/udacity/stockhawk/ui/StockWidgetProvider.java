package com.udacity.stockhawk.ui;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by arogin on 3.4.2017 г..
 */

public class StockWidgetProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;

    public StockWidgetProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
//        initData();
    }

    @Override
    public void onDataSetChanged() {
//        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        return null;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
