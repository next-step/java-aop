package com.interface21.beans.factory;

import com.interface21.beans.factory.proxy.Advisor;

import java.util.List;

public interface Advised {
    void addAdvisor();

    Class<?> getTarget();

    List<Advisor> getAdvisors();
}
