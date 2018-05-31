package com.example.a0104.crolling;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Firebase{
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef = rootRef.child("Group");
    DatabaseReference userRef = rootRef.child("User");

    void updateUser(String id, String name, String table){ // 로그인 후에 firebase 에 연결하여 user 테이블에 id,name,table 이 입력된다.
        Log.d("test","updateUser 호출");
        DatabaseReference childRef = userRef.child(id);
        childRef.child("Name").setValue(name);
        childRef.child("Table").setValue(table);
    }

    String masterGroup(String id, String table, String groupName){ // 조장이 만드는 그룹, 랜덤으로 만들어진 Key 값을 만들어내며, 다른 조원이 들어올 수 있도록한다.
        // 과목명을 Group/(Key)/groupName 속에 넣을 수 있도록 한다.
        Log.d("test","masterGroup 호출");
        String Key = groupRef.push().getKey(); // 그룹을 만들고 그룹의 키값을 가져온다.
        groupRef.child(Key).child(id).setValue(table);
        groupRef.child(Key).child("조 이름").setValue(groupName);
        return Key;
    }

    String slaveGroup(final String Key, final String id, final String table){ // 조원이 조장이 보내준 키값을 통해 들어올 수 있도록한다.
        Log.d("test", "slaveGroup 호출");
        final String[] groupName = new String[1];
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) { // 키값이 존재한다면
                if(dataSnapshot.hasChild(Key)) {
                    groupRef.child(Key).child(id).setValue(table);
                    groupName[0] = dataSnapshot.child(Key).child("조 이름").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { // 키값이 없다면
            }
        });
        return groupName[0];
    }

    void getGroup(final String Key){
        groupRef.child(Key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //리스트뷰 추가해주세요
                Query group = groupRef.child(Key).orderByChild("id");
                Log.d("test", String.valueOf(group));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
