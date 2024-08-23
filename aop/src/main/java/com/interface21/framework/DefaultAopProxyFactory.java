package com.interface21.framework;

public class DefaultAopProxyFactory implements AopProxyFactory {

    private static class Holder {
        private static final DefaultAopProxyFactory INSTANCE = new DefaultAopProxyFactory();
    }

    public static DefaultAopProxyFactory getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public AopProxy createAopProxy(Advised advised) {
        return new CglibAopProxy(advised);
    }
}
