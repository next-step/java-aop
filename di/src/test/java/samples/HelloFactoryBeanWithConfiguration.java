package samples;

import com.interface21.beans.factory.FactoryBean;

public class HelloFactoryBeanWithConfiguration implements FactoryBean<Hello> {
    @Override
    public Hello getObject() throws Exception {
        return Hello.from("kim");
    }
}
