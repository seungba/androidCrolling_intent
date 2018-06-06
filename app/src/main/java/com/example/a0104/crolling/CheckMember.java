package com.example.a0104.crolling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList<String> member;
    ArrayList<String> checkedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_member);
        Intent intent = getIntent();
        member = new ArrayList<>();
        member = intent.getStringArrayListExtra("member");
        checkedTime = new ArrayList<>();

        TableLayout checkTable = findViewById(R.id.checkTable);
        for (int i = 0; i < member.size(); i++)
        {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setOnCheckedChangeListener(this);
            checkBox.setId(i);
            checkBox.setText(member.get(i));
            checkBox.setTextSize(20f);
            checkTable.addView(checkBox);
            i++;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = buttonView.getId();
        if (isChecked == true){
            checkedTime.add(member.get(i+1));
        }
    }
}