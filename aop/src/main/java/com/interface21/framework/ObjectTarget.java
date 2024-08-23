package com.interface21.framework;

public class ObjectTarget implements Target {
    private Object target;

    public ObjectTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object getTarget() {
        return target;
    }
}
