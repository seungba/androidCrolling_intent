package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> timeTable = new ArrayList<>();
    EditText ID,PW;
    private String name;
    private String id,pw;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main","onCreate 호출됨");

        ID = findViewById(R.id.ID); //EditText 아이디 입력창
        PW = findViewById(R.id.PW); //EditText 비밀번호 입력창
        Login = findViewById(R.id.Login); //Button 로그인 버튼

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//클릭 되었을때
                Log.d("LoginBtn","onClick 호출됨");
                id = ID.getText().toString(); //아이디 입력창의 데이터를 들고와서 문자열으로 받는다.
                pw = PW.getText().toString(); //비밀번호 입력창의 데이터를 들고와서 문자열으로 받는다.
                Log.d("LoginBtn","아이디"+id + ", 비밀번호"+pw);

                GetData getData = new GetData();
                getData.start();
                try { //getData 쓰레드 수행시간이 인텐트로 화면이동을 못따라가서 인텐트에 timeTable을 넣을 수 없었다.
                    Log.d("LoginBtn","join"); //getData 쓰레드가 끝날때 까지 기다린다.
                    getData.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                intent.putStringArrayListExtra("timeTable",timeTable); //시간표 넣는다.
                intent.putExtra("name",name);
                intent.putExtra("id",id);
                startActivity(intent);
                Log.d("LoginBtn", "onClick 종료");
            }
        });//이벤트 감지자
    }
    private class GetData extends Thread{  //스레드
        @Override
        public void run() {
            try {
                Connection.Response loginPageResponse =
                        Jsoup.connect("https://sso.daegu.ac.kr/dgusso/ext/lms/login_form.do") //대구대 로그인폼
                                .method(Connection.Method.GET)
                                .userAgent("Mozila")
                                .timeout(0)
                                .execute();

                Map<String, String> cookie1 = loginPageResponse.cookies(); //로그인 전에 쿠키
                Map<String, String> data = new HashMap<>(); //데이터들을 해쉬맵으로 저장한다.
                data.put("Return_Url", "http://lms.daegu.ac.kr/ilos/lo/login_sso.acl"); //로그인 창의 Return_url
                data.put("overLogin", "true");
                data.put("loginName", id); //로그인 데이터
                data.put("password", pw); //비밀번호 데이터

                Connection.Response Response = Jsoup.connect("https://sso.daegu.ac.kr/dgusso/ext/tigersweb/login_process.do") //프로세스 접속
                        .data(data) //해쉬맵 데이터
                        .cookies(cookie1) //로그인 전에 쿠키
                        .timeout(0)
                        .execute();

                Map<String, String> cookie2 = Response.cookies(); //로그인에 성공하면 받는 쿠키 + 로그인 전에 쿠키

                Connection.Response Parse = Jsoup.connect("http://lms.daegu.ac.kr/ilos/lo/login_sso.acl")
                        //Connection.Response Parse = Jsoup.connect("http://lms.daegu.ac.kr/ilos/st/main/pop_academic_timetable_form.acl") //로그인 성공시에 들어가는 창
                        .cookies(cookie2) //로그인하고난 뒤에 들고있어야 하는 쿠키
                        .timeout(0)
                        .execute();

                Document document = Parse.parse(); //정보들을 파싱해온다
                name = document.select("div.login").get(0).select("strong").get(0).toString();
                Elements list = document.select("div.m-box2").get(0).select("li");
                Elements sub_sub = list.select("em");
                Elements sub_time = list.select("span");

                for (int i = 0; i < sub_sub.size(); i++) { //ArrayList 에 넣는 작업
                    timeTable.add(sub_sub.get(i).text());
                    timeTable.add(sub_time.get(i).text());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "시간표를 들고 올 수 없습니다..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}