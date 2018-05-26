package com.example.a0104.crolling;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase{
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef = rootRef.child("Group");
    DatabaseReference userRef = rootRef.child("User");

    void updateUser(String id, String name, String table){ // 로그인 후에 firebase에 연결하여 user테이블에 id,name,table이 입력된다.
        Log.d("test","updateUser 호출");
        DatabaseReference childRef = userRef.child(id);
        childRef.child("Name").setValue(name);
        childRef.child("Table").setValue(table);
    }

    String masterGroup(String id){ // 조장이 만드는 그룹, 랜덤으로 만들어진 Key값을 만들어내며, 다른 조원이 들어올 수 있도록한다.
        String Key;     // 과목명을 Group/(Key)/groupName속에 넣을 수 있도록 한다.
        Log.d("test","updateGroup 호출");
        Key = groupRef.push().getKey();
        groupRef.child(Key).setValue(id);
        return Key;
    }

    void slaveGroup(String Key, String id){ // 조원이 조장이 보내준 키값을 통해 들어올 수 있도록한다.
        Log.d("test", "enterGroup 호출");
        groupRef.child(Key).setValue(id);
    }

    void groupTable(String Key){
        ValueEventListener groupData = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
