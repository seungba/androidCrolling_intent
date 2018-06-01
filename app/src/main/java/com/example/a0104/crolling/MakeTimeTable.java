package com.example.a0104.crolling;

import android.util.Log;

import java.util.ArrayList;
/*
대구대 LMS 에서 크롤링 해온 시간표 데이터(ArrayLsit) 를 파싱한다.
MakeTimeTable -> ArrayList 에는 [과목][시간][과목][시간]... 순으로 저장이 되어있다
              예) 과목 '모바일프로그래밍'  시간 월(09:00~10:15), 목(13:30~14:45)
              과목명이 저장된 상태로 시간을 시간별로 나누어 배열로 저장한다
              시간[0] = 월(09:00~10:15), 시간[1] = 목(13:30~14:45)
              시간을 요일별 분류 후 시간에 따라 timeTable[][]에 저장
maskTable -> MakeTable 에서 만들어진 테이블을 0와 1의 값으로만 저장
            데이터가 있으면 1, 없으면 0으로 저장한다.
*/

class MakeTimeTable {
    public String[][] MakeTable(ArrayList<String> SubList) {
        String[][] timeTable = new String[5][20];
        for(int i=0; i < 5; i++){
            for(int j=0; j < 20; j++){
                timeTable[i][j] = null;
            }
        }
        for (int i = 0; i < SubList.size(); i++) { // 강의마다 시간표에 넣는 작업
            String[] Subject = SubList.get(i).split(" "); // 강의 이름만 추출
            i++; // 넣은 과목의 시간으로 이동
            String[] time = SubList.get(i).toString().split(" "); // 강의 시간이 2개이상일 경우 분리한다.

            for (int j = 0; j < time.length; j++) { // 강의시간 하나를 분류한다.
                int day = 0, cloStart, cloEnd, clo, minStart, minEnd; // 요일, 시간, 분으로 분류
                if (!(time[j].charAt((0)) == '본')) { //본교가상일 경우 스킵
                    switch (time[j].charAt(0)) {
                        case '월':
                            day = 0;
                            break;
                        case '화':
                            day = 1;
                            break;
                        case '수':
                            day = 2;
                            break;
                        case '목':
                            day = 3;
                            break;
                        case '금':
                            day = 4;
                            break;
                    }
                    // 시간계산
                    cloStart = Integer.parseInt(time[j].substring(2, 4)); // 문자형을 상수형으로 바꿔주고 시작시간에 넣는다.
                    cloEnd = Integer.parseInt(time[j].substring(8, 10)); // " 종료시간에 넣는다.
                    clo = (cloEnd - cloStart) * 2; // 진행시간을 계산
                    cloStart = (cloStart - 9)*2; // 시간에 8을 빼서 강의를 교시마다 넣을 수 있도록한다.
                    // 분계산
                    // 시작분은 00,30분 단위로 끊어져 있다. 종료분은 15,45,50으로 되어있어 복잡하다.
                    minStart = Integer.parseInt(time[j].substring(5, 7)); // " 시작분
                    minEnd = Integer.parseInt(time[j].substring(11, 13)); // " 종료분
                    if (minStart >= 30) {
                        cloStart++;
                        clo--;
                    }
                    if (minEnd < 30 && minEnd > 0) {
                        clo++;
                    } else if(minEnd < 60 && minEnd > 30){
                        clo = clo+2;
                    }
                    for (int k = 0; k < clo; k++) {
                        timeTable[day][cloStart] = Subject[0];
                        cloStart++;
                    }
                }
            }
        }
        Log.d("test","timetable완성");
        return timeTable;
    }
    String maskTable(String[][] timeTable){
        String mask ="";
        for(int i=0; i < 5; i++){
            for(int j=0; j < 20; j++){
                if(timeTable[i][j] == null) {
                    mask += "0";
                } else mask += "1";
            }
        }
        return mask;
    }
}
