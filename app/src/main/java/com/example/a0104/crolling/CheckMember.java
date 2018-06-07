package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;

import java.util.ArrayList;

public class CheckMember extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ArrayList<String> member_id;
    ArrayList<String> member_time;
    ArrayList<String> checkedTime;
    Button makeBtn;
    Integer[] memberCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_member);
        makeBtn = findViewById(R.id.makeBtn);

        member_id = new ArrayList<>();
        member_time = new ArrayList<>();
        checkedTime = new ArrayList<>();

        final Intent intent = getIntent();
        TableLayout checkTable = findViewById(R.id.checkTable);
        member_id = intent.getStringArrayListExtra("member_id");
        member_time = intent.getStringArrayListExtra("member_time");

        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i < member_id.size(); i++){
                    if (memberCheck[i] == 1) {
                        checkedTime.add(member_time.get(i));
                        memberCheck[i] = 0;
                    }
                }
                Intent intent1 = new Intent(CheckMember.this, MemberTable.class);
                intent1.putExtra("checkedTime", checkedTime);
                startActivity(intent1);
                finish();
            }
        });

        memberCheck = new Integer[member_id.size()];
        for (int i = 0; i < member_id.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setId(i);
            checkBox.setText(member_id.get(i));
            checkBox.setTextSize(20f);
            checkTable.addView(checkBox);
            memberCheck[i] = 0;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = buttonView.getId();
        if (isChecked == true){
            memberCheck[i] =1;
        } else memberCheck[i] = 0;
    }
}