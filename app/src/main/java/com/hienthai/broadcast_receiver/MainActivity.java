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

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

                List<User> users = getListUser(data);
                String result = "";
                for (int i = 0; i < users.size(); i++) {
                    result += users.get(i).getId() + " _ " + users.get(i).getName() + "\n";
                }

                textView.setText(result);
            }
        }
    };

    private List<User> getListUser(String data) {
        List<User> userList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            User user;
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                user = gson.fromJson(jsonObject.toString(), User.class);
                userList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

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

        User user1 = new User(1, "Hien");
        User user2 = new User(2, "Tra My");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(users).getAsJsonArray();
        String strJson = jsonArray.toString();

        intent.putExtra(KEY_DATA, strJson);
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