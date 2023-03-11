# shoppingmall

## 앞으로 추가할 기능들

1. Service 기능 추가 
옷 추가 - 이미지도 저장되어야함.
전체 통계 - 총 개수, 종류별 개수, 색깔별 개수
옷 정보 변경
선택한 옷 제거

2. 게시판 추가
옷 클릭하면 후기 같은 것들 적을 수 있는 게시판.
답글 구현 - 우선 익명

3. 로그인 기능
회원가입
로그인
나중에 소셜 플랫폼으로 가입하는 거 해보기

4. 어드민 기능
게시판 관리
옷 관리

5. 옷 페이징 기능.
너무 길어지면 다음 페이지에서 볼 수 있도록 함.

6. db 교체하기
어떤 db가 적절한지 고민하고 h2에서 그걸로 교체하기.

--> 졸업. 다음 단계로 넘어가기! 3월 안에 끝내는게 목표.

## 기술적인 도전

1. TDD로 개발하기
서버 연결 안하고 단위 테스트로 짤 수 있으면 짜는게 좋다.
일단 맘대로 하고, 익숙해지면 공부해서 발전시키기

2. 클린코드
일단 맘대로 하고, 후에 공부하기

3. 적절한 db 선택 공부

4. db 설계 공부
아직 할 단계는 아닌 거 같긴함.

5. REST API 형식 맞추기

## 문제 해결

1. db에서 옷 정보 가져올 수 있게 하기. 

data/data.json에서 옷 정보 가져오던 것을 db에서 옷 정보 가져올 수 있게 하는 것이 목적.
하지만, 그 과정이 쉽지 않았다.

Controller에서 model에 json을 담아, js에서 받아서 사용하려고 했는데
js가 server-side가 아닌 client-side이기 때문에 실패함.

그래서, ajax를 사용하고자 했고, 사용법은 Controller에서 `@ResponseBody` 쓰는 것.
ajax는 형식에 맞게 써주면 된다.

문제는 ajax method 내부에서 log를 찍어보면 값이 나오는데, 밖으로 꺼내려하면 null이라는 것.
비동기로 작동하고 있어서 데이터를 쏴주기 전에 ajax가 실행되서 그런 것이었고,
`aysnc : false`를 써주어 문제를 해결함.

기존에는 fetch로 값을 받아와서, Promise에 담아 다루는 형식이었는데,
string을 직접 가져오는 형태로 바뀌어서 `JSON.parse()`를 사용하였고,
return 값이 Object 형식이기 때문에, 그에 맞게 추가적인 코드를 수정했음.