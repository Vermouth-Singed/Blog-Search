## 프로젝트 설명
### - 구조
1. 이력 테이블 (blog_search_history)과 통계용 테이블 (blog_search_cnt) 두개
2. 블로그 검색을 하게되면 검색 결과를 리턴하고, 이력 테이블에 저장
3. 주기적인 스케쥴러를 활용해서 이력 데이터 통계를 10초 마다 통계 테이블에 저장
4. API 통해 검색횟수 많은 순으로 최대 열개의 통계 데이터 조회
5. 블로그 검색 시 카카오 API 를 먼저 조회하는데 오류 발생할 경우, 네이버 API 로 전환
6. 카카오, 네이버 API 에 필요한 키 값들은 Jasypt 로 관리
7. 각 API 별로 다른 Request 컬럼, Response 컬럼들 카카오 API 기준으로 변환
8. 날짜 형식은 yyyy-MM-dd HH:mm:ss 로 통일
### - 테이블
#### blog_search_history
|<span style="color:cyan;">**컬럼명**</span>|<span style="color:cyan;">**설명**</span>|
|:---:|:---:|
|seq|PK|
|source|출처 (K:카카오,N:네이버)|
|query|검색어|
|reg_dttm|검색시간|
#### blog_search_cnt
|<span style="color:cyan;">**컬럼명**</span>|<span style="color:cyan;">**설명**</span>|
|:---:|:---:|
|query|검색어|
|cnt|검색횟수|
### - 스웨거
![](swagger.png)
### - 세팅
1. Environment variables 에 jasypt.key=?? 값 설정 후 실행