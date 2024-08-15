package camp.nextstep.step1

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import net.sf.cglib.proxy.Enhancer
import java.lang.reflect.Proxy

class HelloTest : FunSpec({

    context("dynamic proxy") {
        test("say로 시작하는 메소드의 반환값만 대문자로 변환한다") {
            val hello = Proxy.newProxyInstance(
                Hello::class.java.classLoader,
                arrayOf(Hello::class.java),
                UpperCaseInvocationHandler(HelloTarget())
            ) as Hello

            assertSoftly(hello) {
                sayHello("jin young") shouldBe "HELLO JIN YOUNG"
                sayHi("jin young") shouldBe "HI JIN YOUNG"
                sayThankYou("jin young") shouldBe "THANK YOU JIN YOUNG"
                pingPong("jin young") shouldBe "Ping Pong jin young"
            }
        }
    }

    context("cglib proxy") {
        test("say로 시작하는 메소드의 반환값만 대문자로 변환한다") {
            val enhancer = Enhancer()
            enhancer.setSuperclass(HelloTarget::class.java)
            enhancer.setCallback(UpperCaseMethodInterceptor())
            val actual = enhancer.create() as HelloTarget

            assertSoftly(actual) {
                sayHello("jin young") shouldBe "HELLO JIN YOUNG"
                sayHi("jin young") shouldBe "HI JIN YOUNG"
                sayThankYou("jin young") shouldBe "THANK YOU JIN YOUNG"
                pingPong("jin young") shouldBe "Ping Pong jin young"
            }
        }
    }
})
