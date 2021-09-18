package com.example.readingnetworkstate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = "";
    LogView mLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView logTextView = findViewById(R.id.log_view);
        mLogView = new LogView(logTextView, LOG_TAG);

        ReadingNetworkState readingNetworkState = new ReadingNetworkState(this, mLogView);
        readingNetworkState.gettingInstantaneousState();
        readingNetworkState.additionalNetworks();
    }
}