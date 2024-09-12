package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.Advised;
import com.interface21.beans.factory.proxy.cglib.CglibAopProxy;
import com.interface21.framework.AopProxy;

public class DefaultAopProxyFactory implements ProxyFactory {

    @Override
    public AopProxy createAopProxy(Advised advised) {
        return new CglibAopProxy(advised);
    }
}
