package com.interface21.beans.factory.proxy;

import com.interface21.beans.factory.Advised;
import com.interface21.framework.AopProxy;

public interface ProxyFactory {

    AopProxy createAopProxy(Advised advised);
}
