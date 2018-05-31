package com.example.a0104.crolling;

import android.telecom.Call;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase {
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
    
    public void slaveGroup(final String Key, String id, String table) {
        final String[] groupName = new String[1];
        final Callback callback = null;
        groupRef.child(Key).child(id).setValue(table);
        Log.d("test","1번");
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(Key)) {
                    callback.success(dataSnapshot.child(Key).child("조 이름").getValue(String.class).toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    public interface Callback{
        String success(String data);
    }
}