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

---

# 요구사항 
## 🚀 1단계 - JDK Proxy와 CGLIB Proxy
- 문자열을 반환하는 코드에 프록시 적용하기 
  - 인터페이스와 구현 클래스가 있을 때 **모든 메서드의 반환 값을 대문자로 변환한다**
- [x] JDK Dynamic Proxy로 구현하기 
- [x] CGLIB 으로 구현하기 
- [x] say로 시작하는 메서드에 한해서만 메서드의 반환 값을 대문자로 변환하기 

## 🚀 2단계 - Proxy와 Bean 의존관계
### 목표 
- Proxy 객체를 빈으로 등록하는 것이 최종 목표입니다.

### 문제 
- Proxy 객체는 `@Component`를 이용해 빈을 등록하는 것이 불가능하기 때문에 빈을 등록할 수 있는 방법이 따로 필요합니다. 
- 현재 DI 컨테이너는 Proxy 객체를 등록할 방법이 마련되어 있지 않습니다.
  - 물론, `@Configuration` + `@Bean` 조합으로 등록하는 방법도 있지만, 학습을 위해 팩토리빈을 이용하기로 하였습니다. 

### DI 컨테이너의 Bean과 Proxy를 연결하기
자바 객체가 특정 interface를 구현한 경우 빈을 생성할 때 예외 처리를 할 수 있도록 DI 컨테이너를 개선한다
```java
public interface FactoryBean<T> {
    T getObject() throws Exception;
}
```
- [x] FactoryBean 인터페이스를 구현하는 Bean은 구현체를 DI에 등록하는게 아니고, `getObject()`메서드가 반환하는 Bean을 등록해야 한다 
  - `DefaultListableBeanFactory`에 해당 기능을 구현하였습니다. 아래의 메서드를 참고해 주세요!
    - `private Object getObjectForBeanInstance(final Object beanInstance) {}`
    - 메서드 네이밍은 Spring에 구현된 메서드 이름을 똑같이 따라해 보았습니다. 


### 재사용 가능한 FactoryBean 만들기
Proxy가 추가될 때 마다 FactoryBean을 생성하는 것은 귀찮은 일이다. 공통으로 사용될만한 FactoryBean을 만들자 
- [x] Target, Advice, PointCut을 연결해 Proxy를 생성하는 재사용 가능한 FactoryBean 추가하기
  - `ProxyFactoryBean`이라는 클래스를 통해 구현하였습니다. 