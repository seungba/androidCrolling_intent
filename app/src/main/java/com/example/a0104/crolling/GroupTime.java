package com.example.a0104.crolling;

import java.util.ArrayList;

public class GroupTime { // 그룹 내의 멤버 n개를 상대로 빈시간을 골라내는 코드
    public ArrayList<Integer> GroupTime(String mask) { // mask 테이블을 요일별로 5개의 ArrayList 로 나눈다
        ArrayList<Integer> table = new ArrayList<>();
        table.add(Integer.parseInt(mask.substring(0,20)));
        table.add(Integer.parseInt(mask.substring(20,40)));
        table.add(Integer.parseInt(mask.substring(40,60)));
        table.add(Integer.parseInt(mask.substring(60,80)));
        table.add(Integer.parseInt(mask.substring(80,10)));
        return table;
    }
}
