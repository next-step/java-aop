package samples;

import com.interface21.beans.factory.FactoryBean;

public class HelloTargetFactoryBean implements FactoryBean<HelloTarget> {

    @Override
    public HelloTarget getObject() {
        return new HelloTarget();
    }
}
