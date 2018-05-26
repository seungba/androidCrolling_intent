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
    String user_name, user_id, mask;

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
                intent.putExtra("id",user_id);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        intent.getStringArrayListExtra("timeTable");
        user_name = intent.getStringExtra("name"); // html 코드 그대로 들고오기때문에 수정이 필요하다.
        user_id = intent.getStringExtra("id");
        String[] array1 = user_name.split(">"); //<strong class=\"site-font-color\" id=\"user\" style=\"float: left ;letter-spacing:-1px;\">김민석</strong>"
        String[] array2 = array1[1].split("<"); //김민석</strong>
        user_name = array2[0];
        Log.d("Sub",user_name);

        MakeTimeTable makeTimeTable = new MakeTimeTable();
        timeTable = makeTimeTable.MakeTable(intent.getStringArrayListExtra("timeTable")); //시간표 만들기를 통해 개인 시간표를 만든다.
        mask = makeTimeTable.maskTable(timeTable); // Firebase에서 쓰이는 mask 시간표를 만든다.

        Firebase firebase = new Firebase(); // User테이블에 정보(학번(id), 이름(name), 시간표(timeTable)을 넣는다)
        firebase.updateUser(user_id, user_name, mask);
    }
}
