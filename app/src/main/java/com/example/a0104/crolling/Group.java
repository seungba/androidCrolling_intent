package com.example.a0104.crolling;

public class Group {
    public String groupName, Key;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Group(String groupName, String Key) {
        this.groupName = groupName;
        this.Key = Key;
    }
}
