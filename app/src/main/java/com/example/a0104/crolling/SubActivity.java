package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubActivity extends AppCompatActivity {

    String [][] timeTable;
    Button groupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Sub","onCreate 호출");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        groupBtn = findViewById(R.id.groupBtn); //검색
        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override //이벤트 감지자 등록
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, GroupActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        intent.getStringArrayListExtra("timeTable");
        intent.getStringExtra("name");
        intent.getStringExtra("id");
        Log.d("Sub",intent.getStringArrayListExtra("timeTable").get(0).toString());
        Log.d("Sub",intent.getStringExtra("name"));
        Log.d("Sub",intent.getStringExtra("id"));

        MakeTimeTable makeTimeTable = new MakeTimeTable();
        timeTable = makeTimeTable.MakeTable(intent.getStringArrayListExtra("timeTable"));
    }
}
