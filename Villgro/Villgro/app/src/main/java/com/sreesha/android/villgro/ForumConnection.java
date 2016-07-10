package com.sreesha.android.villgro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.sreesha.android.villgro.Networking.APIUrls;

public class ForumConnection extends AppCompatActivity {

    String[] items = new String[]{"Q1", "Q2", "Q3", "Q4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.e("URL"
                , APIUrls
                        .getSignInURL(
                                "kssreesha@gmail.com"
                                , "YOLO"
                                , "Social EntrePreneur")
                        .build().toString());
    }
}
