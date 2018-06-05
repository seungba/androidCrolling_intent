package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
/*
LoadingActivity 에서 바로 올 수도 있고, MainActivity 에서 올 수도 있다.
기본 메인화면이다 -> 시간표를 보여준다 (미완성)
                -> 조별과제 액티비티로 갈때 학번, 테이블 마스크를 보낸다.
                -> 로그인을 통해서 시간표 업데이트가 가능하다 (MainActivity)
*/

public class SubActivity extends AppCompatActivity { // 메인 액티비티에서 가져온 값으로 테이블을 만들고, 데이터를 저장하기 위한 액티비티

    Button groupBtn, LogBtn;
    TextView nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Sub","onCreate 호출");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String name = intent.getStringExtra("name");
        final String table = intent.getStringExtra("table");


        ArrayList<String> timeTable;
        timeTable = GroupTime(table);
        MONDAY_0(timeTable.get(0));//timeTable.get(0)
        tuesday_0(timeTable.get(1));
        wednesday_0(timeTable.get(2));
        thursday_0(timeTable.get(3));
        friday_0(timeTable.get(4));

        nameView = findViewById(R.id.nameView);
        nameView.setText(name);

        groupBtn = findViewById(R.id.groupBtn); //검색
        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override //이벤트 감지자 등록
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this, GroupActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("table", table);
                startActivity(intent);
            }
        });

        LogBtn = findViewById(R.id.LogBtn);
        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    public ArrayList<String> GroupTime(String mask) { // mask 테이블을 요일별로 5개의 ArrayList 로 나눈다
        ArrayList<String> table = new ArrayList<>();
        table.add(mask.substring(0, 20));
        table.add(mask.substring(20, 40));
        table.add(mask.substring(40, 60));
        table.add(mask.substring(60, 80));
        table.add(mask.substring(80, 100));
        return table;
    }

    void MONDAY_0(String mon){
        for(int i = 0; i < 20; i++){
            int getid = getResources().getIdentifier("monday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            textView.setBackgroundColor(getColor(R.color.Table_row_1));
        }
    }
    void tuesday_0(String tue) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("tuesday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            textView.setBackgroundColor(getColor(R.color.Table_row_2));
        }
    }
    void wednesday_0(String wed) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("wednesday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            textView.setBackgroundColor(getColor(R.color.Table_row_3));
        }
    }
    void thursday_0(String tue) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("thursday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            textView.setBackgroundColor(getColor(R.color.Table_row_4));
        }
    }
    void friday_0(String fri) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("friday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            textView.setBackgroundColor(getColor(R.color.Table_row_5));
        }
    }

}
