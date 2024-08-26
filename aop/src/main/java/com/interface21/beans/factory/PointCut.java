package com.interface21.beans.factory;

public interface PointCut {
    MethodMatcher getMethodMatcher();

    PointCut TRUE = TruePointcut.INSTANCE;
}
