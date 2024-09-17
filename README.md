# 만들면서 배우는 스프링
[Next Step - 과정 소개](https://edu.nextstep.camp/c/4YUvqn9V)

## @Transactional 구현하기

### 학습목표
- AOP 구현을 통해 AOP와 Proxy에 대한 이해도를 높인다.

### 시작 가이드
1. 이전 미션에서 진행한 코드를 사용하고 싶다면, 마이그레이션 작업을 진행합니다.
    - 학습 테스트는 강의 시간에 풀어봅시다.
2. 학습 테스트를 완료하면 LMS의 1단계 미션부터 진행합니다.

## 준비 사항
- IntelliJ에 Kotest 플러그인 설치
- 하단의 cglib 사용시 주의사항 참고

## 학습 테스트
- 실패하는 학습 테스트를 통과시키시면 됩니다.
- 학습 테스트는 aop 패키지 또는 클래스 단위로 실행하세요.

AOP와 스프링 AOP에 대해 좀 더 자세히 알아봅시다.

AOP에서 중요한 개념은 🌟로 표시했습니다.

🌟가 붙은 단어의 설명은 주의 깊게 읽어보세요.

1. [AOP 기본 개념](study/src/test/kotlin/aop/Concepts.kt)
2. [스프링 AOP](study/src/test/kotlin/aop/SpringAOP.kt)
3. [@AspectJ](study/src/test/kotlin/aop/AspectJ.kt)

### Plain POJO call
<img src="docs/images/aop-proxy-plain-pojo-call.png" alt="plain-pojo">

### Proxy call
<img src="docs/images/aop-proxy-call.png" alt="proxy">

### JDK Proxy와 CGLib Proxy 비교
![](docs/images/spring-aop.png)

## 학습 테스트에서 cglib 사용시 주의사항

cligb를 구현할 때 스샷을 참고해서 아래 VM 옵션을 활성화한다.

```
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/java.lang.reflect=ALL-UNNAMED
--add-opens java.base/java.util=ALL-UNNAMED
```

우측 상단 Run / Debug Configurations 메뉴

![](docs/images/edit-configurations.png)

![](docs/images/modify-options.png)

![](docs/images/add-vm-options.png)

![](docs/images/input-options.png)


## 1단계 요구사항
- [x] cglib 대문자 반환하게 만든다.
- [x] dynamic proxy 대문자 반환하게 만든다.



## 2단계 요구사항

- [x] 1. DI 컨테이너의 Bean과 Proxy 를 연결
  - 기능
    - [x] Factory Bean에서 CGLIB 프록시 객체를 생성한다.
- [x] 2. 재사용 가능한 Factory Bean
  - 기능
    - [x] Target 연결가능
      - 대상 클래스
    - [x] Advice 연결가능
      - 비즈니스 로직 전후에 들어갈 로직들을 실행한다. (Interceptor)
    - [x] PointCut 연결가능
      - Matcher로 method 특정한다.
피드백

- [x] Target, Advisor, Advice, PointCut, JoinPoint 에 대한 키워드 설명
  AOP 란 OOP로 코드 중복을 극복할 수 없는 상황에서, 코드 단위를 주입하기 위해 사용합니다.

스프링에서는 보안, 로깅, 캐싱의 기능들을 프록시 패턴을 이용해 구현하게 됩니다.
Target 이란, 보안, 로깅, 캐싱 과 같은 부가기능들을 적용할 대상으로, ElementType으로 지정되어 메서드, 패키지, 파라미터, 필드, 생성자들이 대상이 될 수 있습니다.
Advisor 이란, PointCut(부가기능 적용 위치)과 Advice(부가기능)를 가지고 Proxy 기능을 실행하기 위한 재료를 가지는 책임을 가집니다. [Advisor 인터페이스]
Advice 이란, 부가기능으로, Pointcut으로 선정된 JoinPoint에 실행될 코드입니다. [Interceptor 인터페이스]
  - @Before, @AfterReturning, @AfterThrowing, @After 와 같은 언제라는 개념도 여기 적용 됩니다.
PointCut 이란, Advice를 적용할 조건들을 나열합니다. [PointCut 인터페이스]
Joinpoint 이란, Advice를 적용할 위치로, PointCut 조건들에 의해 추려진 곳을 이야기합니다. [Invocation 인터페이스] 
  - 메서드 호출시, 변수에 접근할 때, 객체를 초기화 할때, 객체에 접근할때 라는 언제 개념도 여기 적용됩니다.

- [x] Target, Advisor, Advice, PointCut, JoinPoint 에 대한 객체 구현
- [x] Target, Advisor, Advice, PointCut, JoinPoint 에 대한 단위 테스트 구현
- [] FactoryBean을 Bean으로 등록하는 과정 대신에, @Transactional 어노테이션을 붙여서 자동화 하는 방법은 무엇일까요?


## 3단계 요구사항 

- [] 트랜잭션 처리를 하고싶은 메서드에 @Transactional을 추가하면 트랜잭션 처리가 된다.
- [] UserService인터페이스 삭제, AppUserService 클래스를 UserService로 일므 변경
- [] TxUserService 클래스의 트랜잭션 처리 로직을 cglib 프록시로 옮기고, TxUserService를 삭제
- [] FactoryBean으로 프록시를 생성한다. (메서드 레벨, 클래스 레벨 애노테이션을 설정한다.)
- [] 모듈간 서로 의존하는 구조가 되므로, 트랜잭션 처리로직은 tx 모듈로 분리한다.


