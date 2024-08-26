package com.interface21.beans.factory;

public class TruePointcut implements PointCut {

    public static final TruePointcut INSTANCE = new TruePointcut();

    @Override
    public MethodMatcher getMethodMatcher() {
        return MethodMatcher.TRUE;
    }
}
