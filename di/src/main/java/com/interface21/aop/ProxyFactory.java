package com.interface21.aop;

import com.interface21.aop.advice.AroundAdvice;
import com.interface21.aop.advice.JoinPoint;
import com.interface21.aop.advisor.Advisor;
import com.interface21.aop.advisor.Pointcut;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {
    private final Object target;
    private final Class<?>[] interfaces;
    private final List<Advisor> advisors;

    public ProxyFactory(Object target, Class<?>[] interfaces) {
        this.target = target;
        this.interfaces = interfaces;
        this.advisors = new ArrayList<>();
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(
                getClass().getClassLoader(),
                interfaces,
                (proxy, method, args) -> {
                    Object result = method.invoke(target, args);

                    if (advisors.isEmpty()) {
                        return result;
                    }

                    // 일단 하나만 처리하자
                    Advisor advisor = advisors.get(0);

                    Pointcut pointcut = advisor.getPointcut();
                    if (checkPointcutCondition(method, pointcut)) {
                        AroundAdvice advice = (AroundAdvice) advisor.getAdvice();
                        JoinPoint jp = createJoinPoint(method, args);
                        return advice.invoke(jp);
                    }

                    return result;
                }
        );
    }

    private boolean checkPointcutCondition(Method method, Pointcut pointcut) {
        if (pointcut == null) {
            return true;
        }
        return pointcut.matches(method, target.getClass());
    }

    private JoinPoint createJoinPoint(Method method, Object[] args) {
        return new JoinPoint(target, method, args);
    }
}
