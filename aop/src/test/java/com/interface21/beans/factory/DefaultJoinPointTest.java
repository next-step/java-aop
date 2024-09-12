package com.interface21.beans.factory;

import com.interface21.beans.factory.config.AdvisedSupport;
import com.interface21.beans.factory.proxy.Advisor;
import com.interface21.beans.factory.proxy.DefaultAdvisor;
import com.interface21.beans.factory.proxy.advice.Interceptor;
import com.interface21.beans.factory.proxy.factory.ProxyFactoryBean;
import com.interface21.beans.factory.proxy.pointcut.Pointcut;
import com.interface21.beans.factory.proxy.pointcut.TrueMethodMatcher;
import net.sf.cglib.proxy.Enhancer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultJoinPointTest {


}
