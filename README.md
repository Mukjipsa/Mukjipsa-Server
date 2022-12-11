# Mukjipsa-Server
먹집사 서버 🍙
## 먹집사 서버가 앞으로 해야할 일

---

- 에러 내성
    - 현재 에러 핸들링이 하나도 안되고 있음.
        - ex) 조회할 값이 없을 때…
    - reference

  [[Spring Boot] @ControllerAdvice을 이용한 Exception 처리](https://bamdule.tistory.com/92)

- 테스트 코드 작성
    - 테스트 코드가 한 줄 도 없음.
    - 리팩토링에 대한 내성을 키워줌.
    - reference

  [SpringBoot 1.4.0 Test 적용하기 (1)](https://jojoldu.tistory.com/33)

- redis 클러스터의 도입(미정)
    - redis 클러스터를 달면서 redis cluster에 대해 이해하고 공부하는 경험은 도움이 됨.
    - 어떻게??
        - 스터디를 해볼까 고민중…
    - reference

  [Redis Cluster 동작방식](https://findmypiece.tistory.com/133)

  [5분 안에 구축하는 Redis-Cluster](https://co-de.tistory.com/24)

- DTO 정리
    - controller에 나가는 dto가 같은 형식이 굉장히 중복되고 있음.
        - generic을 통해 응답 dto의 중복을 최소화
    - facade까지 entity가 올라오고 해당 layer에서 dto로 변환하고 있음. 해당 책임을 service로 내리자.
        - facade에서는 controller로 나가는 dto만 만든다.