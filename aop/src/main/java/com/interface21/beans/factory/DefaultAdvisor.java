package com.interface21.beans.factory;

public class DefaultAdvisor implements Advisor {
    private final Advice advice;
    private final PointCut pointCut;

    public DefaultAdvisor(final Advice advice) {
        this(advice, PointCut.TRUE);
    }

    public DefaultAdvisor(final Advice advice, final PointCut pointCut) {
        this.advice = advice;
        this.pointCut = pointCut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public PointCut getPointCut() {
        return pointCut;
    }
}
