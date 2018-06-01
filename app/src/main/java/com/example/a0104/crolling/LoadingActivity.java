package com.example.a0104.crolling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
처음 앱을 실행시킬 때 나오는 액티비티
prefs 에서 Name 값을 들고온다.(데이터가 없을 시에 null 값)
Name 값이 null -> MainActivity 로 가서 로그인
Name 값이 User 의 이름 (데이터가 있다) -> SubActivity
*/
public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        SharedPreferences prefs =getSharedPreferences("User", MODE_PRIVATE);
        String name = prefs.getString("Name", null); //키값, 디폴트값
        String id = prefs.getString("ID", null);
        String table = prefs.getString("tableMask", null);

        try { // 잠시 대기하며 로딩페이지를 보여준다.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(name == null) { //데이터가 들어있는지 확인
            Intent intent1 = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent1);
        } else {
            Intent intent2 = new Intent(LoadingActivity.this, SubActivity.class);
            intent2.putExtra("id",id);
            intent2.putExtra("name",name);
            intent2.putExtra("table",table);
            startActivity(intent2);
        }
    }
}
