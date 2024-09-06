package samples;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.MethodInvocation;

public class HelloAroundAdvice implements AroundAdvice {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        return "Hello, " + methodInvocation.proceed() + "!";
    }
}
