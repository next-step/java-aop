package samples;

import com.interface21.beans.factory.proxy.factory.FactoryBean;
import com.interface21.context.stereotype.Component;

@Component
public class SampleFactoryBean implements FactoryBean<Sample> {

    @Override
    public Sample getObject() throws Exception {
        return new Sample();
    }
}
