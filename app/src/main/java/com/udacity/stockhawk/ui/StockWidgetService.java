package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by arogin on 3.4.2017 г..
 */

public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetProvider(this, intent);
    }
}
