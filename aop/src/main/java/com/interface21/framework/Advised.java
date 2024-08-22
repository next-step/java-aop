package com.interface21.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Advised {

    private final Class<?> target;
    private final List<Advisor> advisors;

    public Advised(Class<?> target, List<Advisor> advisors) {
        this.target = target;
        this.advisors = advisors;
    }

    public Advised(Class<?> target) {
        this.target = target;
        this.advisors = new ArrayList<>();
    }

    public Advised(Class<?> target, Advisor advisor) {
        this.target = target;
        this.advisors = List.of(advisor);
    }

    public Class<?> getTarget() {
        return target;
    }

    public void addAdvisor(Advisor advisor) {
        advisors.add(advisor);
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void before(MethodInvocation invocation) {
        invokeAdvice(invocation, Advisor::isBeforeAdvice);
    }

    public void after(MethodInvocation invocation) {
        invokeAdvice(invocation, Advisor::isAfterAdvice);
    }

    public void afterReturning(MethodInvocation invocation) {
        invokeAdvice(invocation, Advisor::isAfterReturningAdvice);
    }

    private void invokeAdvice(MethodInvocation invocation, Predicate<Advisor> filter) {
        advisors.stream()
            .filter(filter)
            .forEach(advisor -> invoke(invocation, advisor));
    }

    private void invoke(MethodInvocation invocation, Advisor advisor) {
        try {
            advisor.invoke(invocation);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
