package com.interface21.beans.factory;

public interface JointPoint {

    Object proceed() throws Throwable;

    Object[] getArgs();

    Object getTarget();

    String getSignature();
}
