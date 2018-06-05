package com.example.a0104.crolling;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupTable extends AppCompatActivity {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef = rootRef.child("Group");
    TextView group_groupName, group_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_table);
        group_groupName = findViewById(R.id.group_groupName);
        group_key = findViewById(R.id.group_key);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        String key = intent.getStringExtra("key");


        final ArrayList<String> memberTable = new ArrayList<>();
        Query group_query = groupRef.child(key);
        group_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RadioButton radioButton = new RadioButton(getBaseContext());
                radioButton.setId(Integer.parseInt(dataSnapshot.getKey()));
                radioButton.setText(dataSnapshot.getKey());
                radioButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
