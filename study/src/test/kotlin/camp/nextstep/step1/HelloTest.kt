package camp.nextstep.step1

import camp.nextstep.aop.CglibAopProxy
import camp.nextstep.aop.JdkDynamicAopProxy
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.lang.reflect.Proxy

class HelloTest : FunSpec({

    context("java dynamic proxy") {
        test("use JdkDynamicAopProxy") {
            val jdkDynamicAopProxy = JdkDynamicAopProxy()
            jdkDynamicAopProxy.setInterfaces(Hello::class.java)
            jdkDynamicAopProxy.addAdvice(UpperCaseInvocationHandler(HelloTarget()))
            val hello = jdkDynamicAopProxy.proxy as Hello

            val actual = hello.sayHello("jin young")
            actual shouldBe "HELLO JIN YOUNG"
        }

        test("use Proxy") {
            val hello = Proxy.newProxyInstance(
                Hello::class.java.classLoader,
                arrayOf(Hello::class.java),
                UpperCaseInvocationHandler(HelloTarget())
            ) as Hello

            val actual = hello.sayHi("jin young")
            actual shouldBe "HI JIN YOUNG"
        }
    }

    context("cglib") {
        test("use CglibAopProxy") {
            val cglibAopProxy = CglibAopProxy()
            cglibAopProxy.addAdvice(UpperCaseMethodInterceptor())
            cglibAopProxy.setTargetClass(HelloTarget::class.java)
            val hello = cglibAopProxy.proxy as HelloTarget

            val actual = hello.sayThankYou("jin young")
            actual shouldBe "THANK YOU JIN YOUNG"
        }
    }
})
