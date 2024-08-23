package samples;

import com.interface21.beans.factory.FactoryBean;

public class SampleFactoryBean implements FactoryBean<SampleBean> {

    @Override
    public SampleBean getObject() throws Exception {
        return new SampleBean("Hello from SampleFactoryBean!");
    }
}

