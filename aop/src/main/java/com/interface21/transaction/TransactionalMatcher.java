package com.interface21.transaction;

import com.interface21.beans.factory.proxy.pointcut.Pointcut;
import java.lang.reflect.Method;

public interface TransactionalMatcher extends Pointcut {
    boolean matches(Method method);
}
