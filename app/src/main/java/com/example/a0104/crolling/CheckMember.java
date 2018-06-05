package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckMember extends AppCompatActivity {
    TextView one,two,three,four;
    ArrayList<String> member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_member);
        Intent intent = getIntent();
        member = new ArrayList<>();
        member = intent.getStringArrayListExtra("member");

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);

        one.setText(member.get(0));
        two.setText(member.get(1));
        three.setText(member.get(2));
        four.setText(member.get(3));
    }
}
