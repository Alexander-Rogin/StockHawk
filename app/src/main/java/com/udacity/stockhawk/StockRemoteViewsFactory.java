package com.udacity.stockhawk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arogin on 5.4.2017 г..
 */

class StockRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Cursor mCursor;
    Context mContext;
    private List<WidgetItem> mWidgetItems = new ArrayList<>();

    public StockRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        Uri uri = Contract.Quote.URI.buildUpon().build();
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                null);

        if (mCursor != null) {
            mWidgetItems.clear();
            while (mCursor.moveToNext()) {
                String symbol = mCursor.getString(mCursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL));
                String price = mCursor.getString(mCursor.getColumnIndex(Contract.Quote.COLUMN_PRICE));
                String change = mCursor.getString(mCursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
                mWidgetItems.add(new WidgetItem(symbol, price, change));
            }
        }
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        WidgetItem widgetItem = mWidgetItems.get(position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
        rv.setTextViewText(R.id.symbol, widgetItem.mSymbol);
        rv.setTextViewText(R.id.price, widgetItem.mPrice);
        rv.setTextViewText(R.id.change, widgetItem.mChange);
        return rv;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private class WidgetItem {
        private String mSymbol;
        private String mPrice;
        private String mChange;

        private WidgetItem(String symbol, String price, String change) {
            mSymbol = symbol;
            mPrice = price;
            mChange = change;
        }
    }
}
