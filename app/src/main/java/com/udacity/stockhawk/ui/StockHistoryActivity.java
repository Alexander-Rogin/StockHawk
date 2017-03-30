package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.udacity.stockhawk.R;

public class StockHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_history);

        Intent parentIntent = getIntent();
        String symbol = parentIntent.getStringExtra(Intent.EXTRA_TEXT);

        Toast.makeText(this, symbol, Toast.LENGTH_LONG).show();
    }
}
