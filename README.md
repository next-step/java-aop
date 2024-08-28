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

### 요구사항 정리

- 문자열을 반환하는 코드에 프록시를 적용해보자.
- 인터페이스와 구현 클래스가 있을 때 모든 메서드의 반환 값을 대문자로 변환한다.
- Java Dynamic Proxy 적용

```java
interface Hello {
    String sayHello(String name);

    String sayHi(String name);

    String sayThankYou(String name);
}

class HelloTarget implements Hello {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }
}
```

- cglib Proxy 적용

```java
class HelloTarget {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }
}
```

- 새로운 메서드 추가
    - HelloTarget 에 say 로 시작하는 메서드 이외에 pingpong() 메서드를 추가한다.
    - say 로 시작하는 메서드에 한해서만 메서드의 반환값을 대문자로 변환해야한다.
    - JDK Dynamic Proxy와 cglib Proxy 모두 동작하도록 구현한다.

```java
public class JdkProxyTest {
    @Test
    void toUppercase() {
        // TODO InvocationHandler와 적용할 메서드 처리 부분을 구현

        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                uppercaseHandler);
        assertThat(proxiedHello.sayHello("javajigi")).isEqualTo("HELLO JAVAJIGI");
        assertThat(proxiedHello.sayHi("javajigi")).isEqualTo("HI JAVAJIGI");
        assertThat(proxiedHello.sayThankYou("javajigi")).isEqualTo("THANK YOU JAVAJIGI");
        assertThat(proxiedHello.pingpong("javajigi")).isEqualTo("Pong javajigi");
    }
}
```

- 이 요구사항을 가장 쉽게 구현하려면 JDK Dynamic Proxy의 InvocationHandler와 cglib Proxy의 MethodInterceptor에서 하드코딩하면 된다.
- 이와 같이 하드코딩하기보다 다음과 같이 Interface를 추가해 추상화해 본다.

```java
import java.lang.reflect.Method;

public interface MethodMatcher {
    boolean matches(Method m, Class targetClass, Object[] args);
}
```

## 2단계 - Proxy 와 Bean 의존관계

### 요구사항

- Bean과 Proxy를 연결하도록 DI 컨테이너를 개선
- DI 컨테이너의 Bean과 Proxy를 연결
    - 자바 객체가 특정 interface를 구현하는 경우 빈을 생성할 때 예외 처리하도록 DI 컨테이너를 개선
    - FactoryBean 인터페이스를 구현하는 Bean이 있다면 FactoryBean 구현체를 DI 컨테이너에 등록하지 않고, getObject() 메소드를 통해 반환되는 Bean을 DI 컨테이너에 등록

```java
public interface FactoryBean<T> {
    T getObject() throws Exception;
}

public class SomeClass {

    public Object someMethod() {
        if (beanInstance instanceof FactoryBean) {
            FactoryBean factory = (FactoryBean) beanInstance;
            beanInstance = factory.getObject();
        }
        return beanInstance;
    }
}
```

- 재사용 가능한 FactoryBean
    - Proxy 가 추가될 때마다 공통적으로 사용할 수 있는 FactoryBean 이 있으면 좋겠다.
    - Target, Advice, PointCut 을 연결해 Proxy 를 생성하는 재사용 가능한 FactoryBean 을 추가한다.

### 정리

- ProxyFactoryBean
    - Proxy 객체를 생성한다.
    - 해당 Proxy 객체는 특정 시점의 전후 부가 로직을 Advisor 를 통해 추가할 수 있다.
- MethodMatcher
    - 특정 메서드가 Advice 의 적용 대상인지 여부를 판단한다.
- PointCut
    - MethodMatcher 통해 특정 시점에서 어떤 메서드가 선택할지 판단한다.
- Advice
    - 메서드 실행될 전 후 로직을 정의한다.
    - MethodBeforeAdvice 는 메서드 실행 전 로직 정의
    - AfterReturningAdvice 는 메서드가 실행 된 후 로직 정의
- Advisor
    - Advice 와 PointCut 을 가지고 어떤 Advice 를 언제 어디에 적용할지를 결정한다

## 3단계 - @Transactional 구현하기

### 요구사항

- 트랜잭션 처리를 하고 싶은 메서드에 @Transactional을 추가하면 트랜잭션 처리가 가능하도록 한다.
- UserService 인터페이스를 삭제하고, AppUserService 클래스를 UserService로 이름을 변경한다.
- TxUserService 클래스의 트랜잭션 처리 로직을 cglib 프록시로 옮기고, TxUserService 클래스를 삭제한다.
- FactoryBean을 사용하여 프록시를 생성하도록 만든다.
- 앞 단계에서 구현한 Bean 컨테이너가 Bean을 생성할 때 @Transactional 애노테이션이 클래스/메소드 레벨에 설정되어 있으면
  Transaction 처리를 할 수 있는 Proxy 를 생성한다.
