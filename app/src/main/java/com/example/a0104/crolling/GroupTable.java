package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GroupTable extends AppCompatActivity {
    Firebase firebase = new Firebase();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_table);
        Intent intent = getIntent();
        String Key = intent.getStringExtra("Key");
    }
}
