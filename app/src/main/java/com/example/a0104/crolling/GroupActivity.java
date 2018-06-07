package com.example.a0104.crolling;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
/*
SubActivity 에서 조별과제 버튼을 통해서 올 수 있다.
Firebase 의 그룹 테이블에서 자신의 학번이 들어있는 그룹만 RecyclerView 로 업데이트한다.
*/
public class GroupActivity extends AppCompatActivity {
    Button addGroupBtn;
    String id, name, table;

    private List<GroupModel> groupList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        table = intent.getStringExtra("table");
        addGroupBtn = findViewById(R.id.addGroupBtn);
        addGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupBox();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final GroupAdapter adapter = new GroupAdapter(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("Group");
        Query query = mRef.orderByChild(id+" "+name).equalTo(table);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("test", "onDataChange " + dataSnapshot.toString());
                groupList.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    GroupModel groupModel = child.getValue(GroupModel.class);
                    groupList.add(groupModel);
                }
                adapter.addGroup(groupList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("test","onCancelled ");
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
        final String[] subject_name = new String[1];

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("조별과제 - 조장");
        builder.setMessage("그룹 이름을 입력해주세요.");
        builder.setView(subject);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                subject_name[0] = subject.getText().toString();
                if (subject_name[0] == ""){
                    emptyError();
                } else {
                    Log.d("test","master 추가");
                    String Key = mRef.push().getKey(); // 그룹을 만들고 그룹의 키값을 가져온다.
                    mRef.child(Key).child(id+" "+name).setValue(table); // id->key, table->value
                    mRef.child(Key).child("groupName").setValue(subject_name[0]); //과목명
                    mRef.child(Key).child("key").setValue(Key); //키
                    mRef.child(Key).child("master").setValue(id+" "+name); // 조장의 권한을 부여하기 위한 child
                }
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
                if (Key == "") {
                    emptyError();
                } else {
                    Log.d("test","slave 추가");
                    mRef.child(Key).child(id+" "+name).setValue(table);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    void emptyError() { //아무것도 입력하지 않았을때 출력한다.
        Toast.makeText(this,"데이터가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
    }

}