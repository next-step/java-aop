package com.interface21.beans.factory.config;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.proxy.Advisor;

import java.util.ArrayList;
import java.util.List;

public class AdvisedSupport implements Advised {

    private final List<Advisor> advisors;
    private Class<?> targetClass;

    public AdvisedSupport() {
        advisors = new ArrayList<>();
    }

    @Override
    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    @Override
    public Class<?> getTarget() {
        return targetClass;
    }

    @Override
    public void setTarget(Class<?> target) {
        this.targetClass = target;
    }

    @Override
    public List<Advisor> getAdvisors() {
        return this.advisors;
    }
}
