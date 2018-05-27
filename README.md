# androidCrolling_intent
시간표 테이블 작성.

읽어들인 시간표 데이터를 시간표에 적용 필요

---------------------------------------

R오류 수정후 커밋하였음

오류가 날 시 아래의 방법을 따라해 보시고 안돼면 연락해주세요

1. androidCrolling_intent / app 하단의 google-services.json 파일을 삭제한 후,

2. https://firebase.google.com/으로 이동 하십시오.

3. 시작하기를 클릭하십시오.

4. "프로젝트 추가"를 클릭하십시오.

5. 프로젝트 이름 : 입력 : sample-app

6. "프로젝트 만들기"를 클릭하십시오 [약 10 초 정도 소요됩니다]

7. google-service.json 파일을 다운로드 한 후, 처음 지웠던 장소(root/app/google-services.json)에 붙여넣기 합니다

8. build.gradle 파일은 이미 수정되어 있습니다. 필요시 수정하세요

9. 우측 상단 Sync Now 하기


--------------------------------

수정사항

* GroupActivity.java

  listView 동적 생성, 저장 미구현, Toast 메시지 

* activity_group.xml

  listView추가 Button크기조절,텍스트 추가 및 위치조절

* activity_main.xml 수정

  사용하지 않던 textview(classes, time) 삭제, 하단에 팀원 이름 새김(삭제해도 무방)

