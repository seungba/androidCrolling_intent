package com.example.a0104.crolling;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class GroupTable extends AppCompatActivity {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef = rootRef.child("Group");
    ArrayList<String> member_id;
    ArrayList<String> member_time;
    ArrayList<String> checkedTime;
    Button check;
    TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_table);
        check = findViewById(R.id.check);
        count = findViewById(R.id.count);

        Intent intent = getIntent();
        final String groupName = intent.getStringExtra("groupName");
        final String key = intent.getStringExtra("key");

        member_id = new ArrayList<>();
        member_time = new ArrayList<>();
        final int[] memberCount = {0};
        Query group_query = groupRef.child(key);
        group_query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!(dataSnapshot.getValue().equals(groupName) || dataSnapshot.getValue().equals(key))) {
                    member_id.add(dataSnapshot.getKey());
                    member_time.add(String.valueOf(dataSnapshot.getValue()));
                    memberCount[0]++;
                    count.setText("현재 조원 수: "+ memberCount[0] +"명");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GroupTable.this, CheckMember.class);
                intent1.putExtra("member_id", member_id);
                intent1.putExtra("member_time", member_time);
                startActivity(intent1);
                finish();
            }
        });


        //CheckMember 에서 체크박스 선택 후 실행
        checkedTime = new ArrayList<>();
        checkedTime = intent.getStringArrayListExtra("checkedTime");
        if (checkedTime.size() > 1){    //checkedTime 의 길이가 1개 이상(CheckMember 에서 인텐트로 왔다.)
            //시간표 만들기
            Log.d("test", checkedTime.get(0));
        }
    }
}
