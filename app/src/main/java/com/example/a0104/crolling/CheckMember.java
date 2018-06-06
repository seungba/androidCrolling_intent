package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CheckMember extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    ArrayList<String> member_id;
    ArrayList<String> member_time;
    ArrayList<String> checkedTime;
    Button makeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_member);
        makeBtn = findViewById(R.id.makeBtn);

        member_id = new ArrayList<>();
        member_time = new ArrayList<>();
        checkedTime = new ArrayList<>(member_id.size());

        final Intent intent = getIntent();
        TableLayout checkTable = findViewById(R.id.checkTable);
        member_id = intent.getStringArrayListExtra("member_id");
        member_time = intent.getStringArrayListExtra("member_time");

        makeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CheckMember.this, GroupTable.class);
                intent1.putExtra("checkedTime", checkedTime);
                startActivity(intent1);
                finish();
            }
        });

        for (int i = 0; i < member_id.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setId(i);
            checkBox.setText(member_id.get(i));
            checkBox.setTextSize(20f);
            checkTable.addView(checkBox);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = buttonView.getId();
        if (isChecked == true) checkedTime.add(member_time.get(i));
    }
}