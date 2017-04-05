package com.udacity.stockhawk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.jjoe64.graphview.series.DataPoint;
import com.udacity.stockhawk.data.Contract;

import java.util.ArrayList;
import java.util.List;

public class StockWidgetProvider implements RemoteViewsService.RemoteViewsFactory {
    ArrayList<QuoteData> mStocks = new ArrayList<>();
    Context mContext = null;

    public StockWidgetProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        updateData();
    }

    private void updateData() {
        Uri uri = Contract.Quote.URI.buildUpon().build();
        Cursor cursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            mStocks.clear();
            while (cursor.moveToNext()) {
                String symbol = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL));
                String price = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE));
                String change = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
                mStocks.add(new QuoteData(symbol, price, change));
            }
        }
    }

    @Override
    public void onDataSetChanged() {
        updateData();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mStocks.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(), R.layout.list_item_quote);
        view.setTextViewText(R.id.symbol, mStocks.get(position).symbol);
        view.setTextViewText(R.id.price, mStocks.get(position).price);
        view.setTextViewText(R.id.change, mStocks.get(position).change);
        return view;
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

    private class QuoteData {
        private String change;
        private String price;
        private String symbol;

        public QuoteData(String symbol, String price, String change) {
            this.change = change;
            this.price = price;
            this.symbol = symbol;
        }
    }
}
