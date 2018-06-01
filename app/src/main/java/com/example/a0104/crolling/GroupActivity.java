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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.xmlpull.v1.XmlPullParser;

public class GroupActivity extends AppCompatActivity {
    Button addGroupBtn;
    String id, name, table;
    RecyclerView groupList;
    DatabaseReference groupRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

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

        groupList = findViewById(R.id.groupList);
        groupList.setLayoutManager(new LinearLayoutManager(this));
        groupRef = FirebaseDatabase.getInstance().getReference("Group");

        FirebaseGroupList();
        Log.d("test","여기까지 호출");
    }
    private void FirebaseGroupList() {
        Log.d("test","그룹리스트 호출");
        Query firebaseGroupQuery = groupRef.orderByChild(id);

        final FirebaseRecyclerOptions<Group> options = new FirebaseRecyclerOptions.Builder<Group>()
                .setQuery(firebaseGroupQuery,Group.class)
                .build();

        FirebaseRecyclerAdapter<Group, GroupViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Group, GroupViewHolder> (options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupViewHolder holder, int position, @NonNull Group model) {
                holder.setDetails(model.getGroupName(),model.getKey());
                Log.d("test","바인더 호출");
            }

            @NonNull
            @Override
            public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d("test","크리에이트 호출");
                View v = LayoutInflater.from(parent.getContext()).inflate((XmlPullParser) groupList, parent, false);
                return new GroupViewHolder(v);
            }
        };
        groupList.setAdapter(firebaseRecyclerAdapter);
    }

    //ViewHolder class
    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public GroupViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setDetails(String group_Name, String group_Key){
            Log.d("test","디테일 호출");
            TextView groupName = mView.findViewById(R.id.groupName);
            TextView Key = mView.findViewById(R.id.Key);

            groupName.setText(group_Name);
            Key.setText(group_Key);
        }
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
                    String Key = groupRef.push().getKey(); // 그룹을 만들고 그룹의 키값을 가져온다.
                    groupRef.child(Key).child(id).setValue(table);
                    groupRef.child(Key).child("조 이름").setValue(subject_name[0]);
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
                    groupRef.child(Key).child(id).setValue(table);
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