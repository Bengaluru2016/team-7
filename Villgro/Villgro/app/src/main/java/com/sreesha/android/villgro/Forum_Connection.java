package com.sreesha.android.villgro;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

public class Forum_Connection extends AppCompatActivity {

    String[] items=new String[]{"Q1","Q2","Q3","Q4"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum__connection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView list = (ListView) findViewById(R.id.questions_listview);
        NewAdap adapter = new NewAdap(this, R.layout.question_row, items);
        list.setAdapter(adapter);


    }

}
