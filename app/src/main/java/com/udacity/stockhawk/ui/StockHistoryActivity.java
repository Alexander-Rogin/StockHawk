package com.udacity.stockhawk.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockHistoryActivity extends AppCompatActivity {
    @BindView(R.id.histroy_graph)
    GraphView mGraph;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_history);
        ButterKnife.bind(this);

        Intent parentIntent = getIntent();
        String symbol = parentIntent.getStringExtra(Intent.EXTRA_TEXT);

        diplayHistory(symbol);
    }

    private void diplayHistory(String symbol) {
        mContext = this;
        Uri uri = Contract.Quote.URI;
        uri = uri.buildUpon().appendPath(symbol).build();

        Cursor cursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            String history = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_HISTORY));
            DataPoint[] quoteDataPoints = parseHistory(history);

            displayGraph(symbol, quoteDataPoints);
        }
    }

    private void displayGraph(String symbol, DataPoint[] dataPoints) {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        series.setDrawBackground(true);
        series.setTitle(symbol);
        mGraph.addSeries(series);

        mGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(mContext));

        mGraph.getViewport().setMaxX(dataPoints[0].getX());
        mGraph.getViewport().setMinX(dataPoints[dataPoints.length - 1].getX());
        mGraph.getViewport().setXAxisBoundsManual(true);
        mGraph.getGridLabelRenderer().setHumanRounding(false);
        mGraph.getLegendRenderer().setVisible(true);
        mGraph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }

    private DataPoint[] parseHistory(String history) {
        Calendar calendar = Calendar.getInstance();
        String[] historicalQuotes = history.trim().split("\n");
        DataPoint[] quoteDataPoints = new DataPoint[historicalQuotes.length];
        for (int i = 0; i < historicalQuotes.length; i++) {
            String quotePair = historicalQuotes[i];
            String[] quoteData = quotePair.split(",");

            long dateMillis = Long.valueOf(quoteData[0].trim());
            calendar.setTimeInMillis(dateMillis);
            Date date = calendar.getTime();

            double quote = Double.valueOf(quoteData[1].trim());

            quoteDataPoints[i] = new DataPoint(date, quote);
        }
        return quoteDataPoints;
    }
}
