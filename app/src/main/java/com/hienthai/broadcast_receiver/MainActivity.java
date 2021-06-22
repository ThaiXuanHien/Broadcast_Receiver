package com.hienthai.broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private InternetBroadcast internetBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        internetBroadcast = new InternetBroadcast();
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(internetBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(internetBroadcast);
    }
}