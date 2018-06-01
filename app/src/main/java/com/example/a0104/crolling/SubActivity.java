package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
            }
        });
    }
}
