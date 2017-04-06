package com.udacity.stockhawk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor != null && mCursor.moveToPosition(position)) {
            String symbol = mCursor.getString(mCursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL));
            String price = mCursor.getString(mCursor.getColumnIndex(Contract.Quote.COLUMN_PRICE));
            String change = mCursor.getString(mCursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
            rv.setTextViewText(R.id.symbol, symbol);
            rv.setTextViewText(R.id.price, price);
            rv.setTextViewText(R.id.change, change);

            final Intent fillInIntent = new Intent();
            fillInIntent.putExtra(Intent.EXTRA_TEXT, symbol);
            rv.setOnClickFillInIntent(R.id.list_item, fillInIntent);

            return rv;
        }
        return null;
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
}
