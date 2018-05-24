package com.example.a0104.crolling;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase{
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef = rootRef.child("Group");
    DatabaseReference userRef = rootRef.child("User");

    void updateUser(String id, String name, String table){
        Log.d("test","updateUser 호출");
        DatabaseReference childRef = userRef.child(id);
        childRef.child("Name").setValue(name);
        childRef.child("Table").setValue(table);
    }

    String masterGroup(String id){
        String Key;
        Log.d("test","updateGroup 호출");
        Key = groupRef.push().getKey();
        groupRef.child(Key).setValue(id);
        return Key;
    }

    void slaveGroup(String Key, String id){
        Log.d("test", "enterGroup 호출");
        groupRef.child(Key).setValue(id);
    }
}
