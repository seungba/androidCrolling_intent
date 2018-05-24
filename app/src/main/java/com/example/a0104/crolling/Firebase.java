package com.example.a0104.crolling;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase{
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference groupRef = rootRef.child("Group");
    DatabaseReference userRef = rootRef.child("User");

    void updateUser(String id, String name){
        Log.d("test","updateUser 호출");
        DatabaseReference childRef = userRef.child(id);
        childRef.child("Name").setValue(name);
    }

    void updateGroup(String groupName, String id,String name){
        Log.d("test","updateGroup 호출");
        DatabaseReference childRef = groupRef.child(groupName);
        childRef.child("Name").setValue(name);
    }
}
