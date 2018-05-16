package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String Cro = "Crolling"; //Crolling 로그 분석
    TextView classes,time;
    EditText ID,PW;
    public String Sub;
    private String id,pw;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Cro,"onCreate 호출됨");

        ID = findViewById(R.id.ID); //EditText 아이디 입력창
        PW = findViewById(R.id.PW); //EditText 비밀번호 입력창
        classes = findViewById(R.id.classes); //TextView 수강과목 출력창
        time = findViewById(R.id.time); //TextView 시간 출력창
        classes.setMovementMethod(new ScrollingMovementMethod()); //출력창이 스크롤 가능하도록
        time.setMovementMethod(new ScrollingMovementMethod());
        Button Login = findViewById(R.id.Login); //Button 로그인 버튼

        Login = (Button)findViewById(R.id.Login);//로그인 버튼 검색
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//클릭 되었을때
                Log.d(Cro,"onClick 호출됨");
                id = ID.getText().toString(); //아이디 입력창의 데이터를 들고와서 문자열으로 받는다.
                pw = PW.getText().toString(); //비밀번호 입력창의 데이터를 들고와서 문자열으로 받는다.
                Log.d(Cro,"아이디"+id + ", 비밀번호"+pw);

                Crolling crolling = new Crolling(); //스레드 생성
                crolling.start();   //스레드 시작
                Log.d(Cro,"아이디와 비밀번호를 입력함.");

                //다음 페이지로 화면을 전환
                //화면을 전환할 때 사용하는 클래스 intent

                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                                                             //이동전 액티비티 ,  이동 할 액티비티
                startActivity(intent); //화면 전환하기
            }
        });//이벤트 감지자

    }

    private class Crolling extends Thread{  //스레드
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

                Document document = Parse.parse();

                ArrayList<String> timeTable = new ArrayList<>();
                Element table = document.select("div.m-box2").get(0);
                Elements time = table.select("li");

                Elements sub_sub = time.select("em");
                Elements sub_time = time.select("span");

                for (Element e : sub_time) {
                    Sub = sub_time.text();
                }


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "시간표를 들고 올 수 없습니다..", Toast.LENGTH_SHORT).show();
            }

            classes.setText(Sub); //출력
        }
    }
}