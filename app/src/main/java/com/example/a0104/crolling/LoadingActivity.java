package com.example.a0104.crolling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);
        startLoading();
    }

    private void startLoading(){
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs =getSharedPreferences("User", MODE_PRIVATE);
                String name = prefs.getString("Name", null); //키값, 디폴트값
                String id = prefs.getString("ID", null);
                String table = prefs.getString("tableMask", null);
                if(name == null) { //데이터가 들어있는지 확인
                    Intent intent1 = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent2 = new Intent(LoadingActivity.this, SubActivity.class);
                    intent2.putExtra("id",id);
                    intent2.putExtra("name",name);
                    intent2.putExtra("table",table);
                    startActivity(intent2);
                    finish();
                }

            }
        }, 0700);
    }
}