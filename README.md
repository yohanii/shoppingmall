# shoppingmall

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