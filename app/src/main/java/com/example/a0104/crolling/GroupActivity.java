package com.example.a0104.crolling;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    Button addGroupBtn;
    String id, name, table;
    String groupName = null;

    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        settingListView(); //
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        table = intent.getStringExtra("table");
        addGroupBtn = findViewById(R.id.addGroupBtn);
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupBox();
            }
        });
    }

    void groupBox() { // addGroup 버튼을 누르면 나오는 다이얼로그박스
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("조별과제");
        builder.setMessage("자신의 역할을 입력해주세요");
        builder.setPositiveButton("조장", new DialogInterface.OnClickListener() {
            @Override   // 조장인 경우에는 최초 그룹을 만든다. (과목명 입력)
            public void onClick(DialogInterface dialog, int which) {
                master();
            }
        });
        builder.setNegativeButton("조원", new DialogInterface.OnClickListener() {
            @Override   // 조원인 경우에는 조장이 만든 그룹을 키값을 통해 들어간다.
            public void onClick(DialogInterface dialog, int which) {
                slave();
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override   // 취소버튼은 나간다.
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
    void master() {
        final EditText subject = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("조별과제 - 조장");
        builder.setMessage("과목명을 입력해주세요.");
        builder.setView(subject);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                groupName = subject.getText().toString();
                Firebase firebase = new Firebase();
                String Key = firebase.masterGroup(id, table, groupName);
                refresh(groupName);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    void slave() {
        final EditText key = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("조별과제 - 조원");
        builder.setMessage("조장에게 받은 키값을 입력해주세요.");
        builder.setView(key);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String Key = key.getText().toString();
                Firebase firebase = new Firebase();
                groupName = firebase.slaveGroup(Key, id, table);
                if (!(groupName==null)){
                    refresh(groupName);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    void settingListView(){
        mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        ListView listView = (ListView)findViewById(R.id.ListView1);
        listView.setAdapter(mAdapter);
    }

    void refresh (String $groupName){
        mAdapter.add($groupName);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this, "데이터가 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }
}