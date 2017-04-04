package com.udacity.stockhawk;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetProvider(this, intent);
    }
}
