package samples;

import com.interface21.beans.factory.FactoryBean;

public class HelloTargetFactoryBean implements FactoryBean<HelloTarget> {

    @Override
    public HelloTarget getObject() {
        return new HelloTarget();
    }

    @Override
    public HelloTarget getObject(final Class<?>[] argumentTypes, final Object[] arguments) {
        return new HelloTarget();
    }

    @Override
    public Class<HelloTarget> getType() {
        return HelloTarget.class;
    }
}
