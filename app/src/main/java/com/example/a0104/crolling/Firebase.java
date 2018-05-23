package com.example.a0104.crolling;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
    }
}
