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

### 🚀 1단계 - JDK Proxy와 CGLIB Proxy
- [x] : java dynamic proxy (메소드의 반환값을 모두 대문자로 반환하도록 변환한다)
- [x] : cglib proxy (인터페이스를 사용하지 않고 구현체만으로 대문자를 반환하도록 변한한다)

### 🚀 2단계 - Proxy와 Bean 의존관계
- [x] : DI 컨테이너의 Bean과 Proxy를 연결 (FactoryBean 구현체를 DI 컨테이너에 등록하지 않고, getObject() 메소드를 통해 반환)
- [x] : 재사용 가능한 FactoryBean (Target, Advice, PointCut을 연결해 Proxy를 생성하는 재사용 가능한 FactoryBean을 추가)

### 🚀 3단계 - @Transactional 구현하기
- [x] : @Transactional 애노테이션을 사용하여 트랜잭션을 적용한다
- [x] : FactoryBean을 사용하여 프록시를 생성하도록 만든다

### 🚀 4단계 - ControllerAdvice, ExceptionHandler 구현하기
- [x] : @ControllerAdvice가 붙은 클래스를 스캔하여 빈으로 등록한다
- [x] : @ExceptionHandler가 붙어 있는 메서드를 찾아 Throwable 타입을 키, HandlerExecution 밸류로 맵핑한다
- [x] : DispatcherServlet에서 발생하는 예외를 HandlerExceptionResolver를 사용하여 처리한다
