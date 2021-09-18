package com.example.readingnetworkstate;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogView {

    private TextView textView;
    private String TAG;

    public LogView(TextView view, String tag) {
        textView = view;
        TAG = tag;

        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    public void log(String text) {
        String time = timeToString() + " : ";
        textView.append(time + text);
        textView.append("\n\n");
        textView.invalidate();
        Log.d(TAG,text);
    }

    private static String timeToString(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
}
