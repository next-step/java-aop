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

## 1단계 - JDK Proxy와 CGLIB Proxy
- java dynamic proxy
  - 메소드의 반환값을 모두 대문자로 반환하도록 변환한다
  - UpperCaseInvocationHandler
    - running target으로 Hello 구현체를 받아 생성한다
- cglib proxy
  - 인터페이스를 사용하지 않고 구현체만으로 대문자 반환하도록 변한한다
  - UpperCaseMethodInterceptor
    - 메소드 실행에서 대문자를 반환하도록 intercept 처리한다

- say로 시작하는 메소드에 대해서만 반환 값을 대문자로 변환하도록 한다

## 2단계 - Proxy와 Bean 의존관계
- ProxyBean
  - getObject에서 cblib을 통한 proxy 객체를 생성하여 반환한다
  - 프록시빈의 타입을 반환한다
  - Enhancer에서 보유중인 Advisors를 돌면서 pointCut과 Advice를 callBack으로 지저안다
- Aspect(Advisor)
  - PointCut과 Advice를 가진다
  - PointCut이 실행가능하도록 선택한 지점에서 Advice를 실행하도록 한다
  - createMethodInterceptor
    - pointCut으로 매칭된 경우 advice를 통해 실행하도록하며 매칭되지 않는 경우 proxy의 기본 실행을 동작시킨다
- PointCut
  - Advice가 적용될 수 있는 지점을 결정한다
  - method가 실행가능한 메소드인지 확인할 수 있다
- Advice
  - 실제로 aop가 실행되는 코드로 실행된다
  - 실행가능한 포인트에서 각 advice 동작을 구성한다
  - JoinPoint를 받아 구현내용을 invoke 처리한다
- Target
  - 실행의 대상이 되는 target class 정보를 가진다
