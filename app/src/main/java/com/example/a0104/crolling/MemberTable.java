package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MemberTable extends AppCompatActivity {
    ArrayList<String> checkedTime;
    ArrayList<String> member;
    ArrayList<String> group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_table);

        checkedTime = new ArrayList<>();
        member = new ArrayList<>();
        group = new ArrayList<>();

        Intent intent = getIntent();
        checkedTime = intent.getStringArrayListExtra("checkedTime");

        for (int i=0; i < checkedTime.size(); i++){
            member = GroupTime(checkedTime.get(i));
            MONDAY_0(member.get(0));//timeTable.get(0)
            tuesday_0(member.get(1));
            wednesday_0(member.get(2));
            thursday_0(member.get(3));
            friday_0(member.get(4));
            member.clear();
        }
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
            if(mon.charAt(i) == '1') {
                textView.setBackgroundColor(getColor(R.color.Table_row_1));
            }
        }
    }
    void tuesday_0(String tue) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("tuesday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            if(tue.charAt(i) == '1') {
                textView.setBackgroundColor(getColor(R.color.Table_row_2));
            }
        }
    }
    void wednesday_0(String wed) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("wednesday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            if(wed.charAt(i) == '1'){
                textView.setBackgroundColor(getColor(R.color.Table_row_3));
            }
        }
    }
    void thursday_0(String tue) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("thursday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            if(tue.charAt(i) == '1') {
                textView.setBackgroundColor(getColor(R.color.Table_row_4));
            }
        }
    }
    void friday_0(String fri) {
        for (int i = 0; i < 20; i++) {
            int getid = getResources().getIdentifier("friday" + i, "id", "com.example.a0104.crolling");
            final TextView textView = findViewById(getid);
            if (fri.charAt(i) == '1') {
                textView.setBackgroundColor(getColor(R.color.Table_row_5));
            }
        }
    }
}
