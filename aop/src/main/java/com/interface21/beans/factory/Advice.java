package com.interface21.beans.factory;

public interface Advice {

    Object around(JointPoint jointPoint);
}
