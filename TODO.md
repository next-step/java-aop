# 🚀 1단계 - JDK Proxy와 CGLIB Proxy

## 미션 설명

jdbc 미션에서 트랜잭션 동기화를 적용하면서 비즈니스 로직과 트랜잭션 처리 로직을 분리했다.
그런데 트랜잭션을 적용하기위해 매번 트랜잭션 처리 로직을 인터페이스를 사용해서 따로 작성하려니 번거롭다.
기능에 따라 비즈니스 로직은 다르지만 트랜잭션을 적용하는 방식은 다르지 않으니 자동으로 트랜잭션 처리 로직을 적용할 수 있으면 좀 더 편리할 것 같다.
트랜잭션 처리 로직이라는 다른 관심사를 분리하기 위해 프록시라는 개념을 적용해보자.

## 기능 요구사항

1단계 예제는 토비의 스프링 책의 AOP에서 인용한 예제이다.

* 문자열을 반환하는 코드에 프록시를 적용해보자.
* 인터페이스와 구현 클래스가 있을 때 모든 메서드의 반환 값을 대문자로 변환한다.

## 1. Java Dynamic Proxy 적용

### 힌트

위 요구사항을 Java에서 기본으로 제공하는 JDK Dynamic Proxy를 적용해 해결한다.
적용 방법은 다음 문서를 참고한다.

## 2. cglib Proxy 적용

앞의 Hello 예제에서 Hello 인터페이스가 없고 구현체 밖에 없다. 이에 대한 Proxy를 생성해 대문자로 반환하도록 한다.

### 힌트

* 위 요구사항을 Java에서 기본으로 제공하는 cglib Proxy를 적용해 해결한다.
* 적용 방법은 다음 문서를 참고한다.
    - CGLIB를 이용한 프록시 객체 만들기
    - baeldung - Introduction to cglib

## 3. 새로운 메서드 추가

* HelloTarget에 say로 시작하는 메서드 이외에 pingpong()과 같은 새로운 메서드가 추가됐다.
* say로 시작하는 메서드에 한해서만 메서드의 반환 값을 대문자로 변환해야한다. 가 동작하도록 한다.
* JDK Dynamic Proxy와 cglib Proxy 모두 동작하도록 구현한다.

### 힌트

* 이 요구사항을 가장 쉽게 구현하려면 JDK Dynamic Proxy의 InvocationHandler와 cglib Proxy의 MethodInterceptor에서 하드코딩하면 된다.
* 이와 같이 하드코딩하기보다 다음과 같이 Interface를 추가해 추상화해 본다.

# 🚀 2단계 - Proxy와 Bean 의존관계

## 기능 요구사항

## 1. DI 컨테이너의 Bean과 Proxy를 연결

자바 객체가 특정 interface를 구현하는 경우 빈을 생성할 때 예외 처리하도록 DI 컨테이너를 개선한다.

* 예를 들어 위와 같은 인터페이스를 구현하는 Bean이 있다면 FactoryBean 구현체를 DI 컨테이너에 등록하지 않고, getObject() 메소드를 통해 반환되는 Bean을 DI 컨테이너에 등록한다. DI
  컨테이너의 내부 동작을 보면 다음과 같이 동작할 것이다.

(소스코드 - https://edu.nextstep.camp/s/I3YzY6iR/ls/UJHO4nzf)

## 2. 재사용 가능한 FactoryBean

* Proxy가 추가될 때마다 FactoryBean을 매번 생성하는 것도 귀찮다. 공통적으로 사용할 수 있는 FactoryBean이 있으면 좋겠다.
* Target, Advice, PointCut을 연결해 Proxy를 생성하는 재사용 가능한 FactoryBean을 추가한다.
