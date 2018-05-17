package com.example.a0104.crolling;

import android.util.Log;
import java.util.ArrayList;

class MakeTimeTable {
    public String[][] MakeTable(ArrayList<String> SubList) {
        String[][] timeTable = new String[5][19];

        Log.d("MakeTable","makeTable 호출");
        for (int i = 0; i < SubList.size(); i++) { //강의마다 시간표에 넣는 작업
            String Subject[] = SubList.get(i).toString().split(" "); //강의 이름만 추출
            i++; //넣은 과목의 시간으로 이동
            String[] time = SubList.get(i).toString().split(" "); //강의 시간이 2개이상일 경우 분리한다.
            Log.d("MakeTable","makeTable 1");

            for (int j = 0; j < time.length; j++) { // 강의시간 하나를 분류한다.
                int day = 0, cloStart, cloEnd, clo, minStart, minEnd, min; // 요일, 시간, 분으로 분류
                Log.d("MakeTable", String.valueOf(time[j].charAt(0)));
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
                Log.d("MakeTable","makeTable 2");
                //시간계산
                cloStart = Integer.parseInt(time[i].substring(2, 4)); //문자형을 상수형으로 바꿔주고 시작시간에 넣는다.
                cloEnd = Integer.parseInt(time[i].substring(8, 10)); // " 종료시간에 넣는다.
                clo = (cloEnd - cloStart)*2; // 진행시간을 계산
                cloStart -= cloStart - 9; // 시간에 8을 빼서 강의를 교시마다 넣을 수 있도록한다.
                Log.d("MakeTable","makeTable 3");
                //분계산
                //시작분은 00,30분 단위로 끊어져 있다. 종료분은 15,45,50으로 되어있어 복잡하다.
                minStart = Integer.parseInt(time[i].substring(5, 7)); // " 시작분
                minEnd = Integer.parseInt(time[i].substring(11, 13)); // " 종료분
                if(minStart >= 30){ cloStart++; }
                if(minEnd < 30) { clo++; }
                Log.d("MakeTable","makeTable 4");
                while (clo == 0) {
                    timeTable[day][cloStart] = Subject[i];
                    clo--;
                    cloStart++;
                }
                Log.d("MakeTable","makeTable 5");
            }
        }
        return timeTable;
    }
}
