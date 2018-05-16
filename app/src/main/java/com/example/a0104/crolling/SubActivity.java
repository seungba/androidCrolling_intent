package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubActivity extends AppCompatActivity {

    Button btn_prev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btn_prev = (Button)findViewById(R.id.btn_prev); //검색
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override //이벤트 감지자 등록
            public void onClick(View v) {

                //이전 페이지로 화면 전환
                /*Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
                화면이 중복되므로 아래의 코드를 사용
                */

                finish(); //현재 액티비티 종료
            }
        });
    }
}
