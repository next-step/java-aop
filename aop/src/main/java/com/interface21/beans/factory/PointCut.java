package com.interface21.beans.factory;

import java.lang.reflect.Method;

public interface PointCut {

    boolean matches(Method method);
}
