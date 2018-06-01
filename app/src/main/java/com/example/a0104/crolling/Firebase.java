package com.example.a0104.crolling;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Firebase {
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("User");

    void updateUser(String id, String name, String table){ // 로그인 후에 firebase 에 연결하여 user 테이블에 id,name,table 이 입력된다.
        Log.d("test","updateUser 호출");
        DatabaseReference childRef = userRef.child(id);
        childRef.child("Name").setValue(name);
        childRef.child("Table").setValue(table);
    }
}