package com.hienthai.broadcast_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MY_ACTION = "MY_ACTION";
    public static final String KEY_DATA = "MY_DATA";
    public static final String DATA = "DATA_THAI_XUAN_HIEN";

    // private InternetBroadcast internetBroadcast; Part 1

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MY_ACTION.equals(intent.getAction())) {
                String data = intent.getStringExtra(KEY_DATA);
                textView.setText(data);
            }
        }
    };

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        // internetBroadcast = new InternetBroadcast(); Part 1


        button.setOnClickListener(v -> {
            clickSendBroadcast();
        });
    }

    private void clickSendBroadcast() {
        Intent intent = new Intent(MY_ACTION);
        intent.putExtra(KEY_DATA, DATA);
        sendBroadcast(intent);

    }

    private void anhXa() {
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
    }

//    Part 1
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        registerReceiver(internetBroadcast, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        unregisterReceiver(internetBroadcast);
//    }


    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(myBroadcastReceiver, new IntentFilter(MY_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }
}