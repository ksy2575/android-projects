<img src="./images/kevinsdream1.png" width="300">
<img src="./images/kevinsdream2.png" width="300">

#### 5초 준다!



   + 개발일정 : 2020.02.15 ~ 2020.02.18
   + 개발인원 : 김수용
   + 개발의도 : 동명의 보드게임 '5초 준다!'를 애플리케이션으로 제작하였습니다.(관련 영상 : https://www.youtube.com/watch?v=ijXDHcSgn2g)
               짧은 기간 내에 직접 사용할 애플리케이션을 만드는 것이 목표였습니다.
             
             
#### 사용시나리오
   1. 게임 시작 버튼을 누른 후, 화면의 카드 뭉치를 터치하면 제시어가 공개됩니다.
   2. 미리 정한 순서에 따른 이번 차례의 진행자가 참여자에게 "(제시어) 3가지!"를 외치며 시작 버튼을 터치합니다.
   3. 앱 내 5초 카운트가 끝날 때까지 첫번째 참여자가 해당 주제에 부합하는 단어를 3가지 말하지 못했다면, 다음 참여자에게 차례가 넘어갑니다.
   4. 한 참여자가 정답을 맞췄다면 해당 참여자의 영역으로 카드를 드래그하여 점수를 추가하고 역할을 변경합니다.

#### 기대효과(다른 앱과의 차별성)
   1. 스마트폰 하나만으로 보드게임을 즐길 수 있음
   2. 친구들과의 모임 등의 자리에서 약 1~2 시간의 놀이가 가능
   3. 원작 보드게임과 달리 애플리케이션 내 파일 수정을 통한 확장이 용이

#### 역할 분담
   - 김수용(팀장)
      + 손글씨 기능 구현
      + runOnUiThread를 활용하여 물체인식 시 말하는 캐릭터 구현
      + inflater를 활용한 환경설정 등 개발
      + bgm, 효과음 제작
      + SQLite를 활용한 DB 구현
      + 엔딩 및 크레딧 구현
      + 배경 및 아이콘 디자인
      + 튜토리얼 구현
      + 듣고 말하기 구현
      + 버튼 디자인
      

#### 시스템 구성도, 순서도

<img src="./images/kevinsdream3.png" width="400"><img src="./images/kevinsdream4.png" width="400">




#### 개발 일정(2019.12.19 ~ 2020.09.22)

<img src="./images/kevinsdream5.png" width="500">




#### 앱 스크린샷




<img src="./images/kevinsdream7.jpg" width="400"><img src="./images/kevinsdream7_2.jpg" width="400">

메인 화면, 튜토리얼

<img src="./images/kevinsdream8.jpg" width="400"><img src="./images/kevinsdream9.jpg" width="400">

스테이지 선택, 문제 선택

<img src="./images/kevinsdream10.jpg" width="400"><img src="./images/kevinsdream11.jpg" width="400">

물체인식부, 듣고 말하기

<img src="./images/kevinsdream12_2.jpg" width="400"><img src="./images/kevinsdream12_3.jpg" width="400">

손글씨 인식부

<img src="./images/kevinsdream13_3.jpg" width="400"><img src="./images/kevinsdream13_4.jpg" width="400">

엔딩



#### 프로젝트 진행 중 느낀 점

   - 주제 선정 및 요구사항 분석 단계에서부터 목표를 명확히 하고 매 순간 확인할 필요가 있습니다. 팀원들끼리 구두로 정해놓은 목표를 따르다보면 기존 생각과는 다른 방향으로 나아갈 수 있기 때문입니다.
   - 액티비티 생명주기 등의 관리를 통한 에러 방지에 많은 시간과 노력을 들여야 합니다. 실제 소비자에게 앱을 배포하기 전에 가능한 모든 경우의 수를 고려해 터치, 시스템 뒤로가기, 홈 화면 이탈 등을 시도해보고 오류를 검출합니다.
   - 구글의 오픈소스 운영체제인 안드로이드를 탑재한 스마트폰은 수없이 많기에 layout_weight와 crop등을 활용하여 최대한 다양한 기기에서 호환이 되도록 설계합니다.
     