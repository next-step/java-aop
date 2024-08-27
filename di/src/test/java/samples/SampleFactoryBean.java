package samples;

import com.interface21.beans.factory.FactoryBean;

public class SampleFactoryBean implements FactoryBean<SampleBean> {

    public static final String MESSAGE = "Hello from SampleFactoryBean!";

    @Override
    public SampleBean getObject() throws Exception {
        return new SampleBean(MESSAGE);
    }
}

