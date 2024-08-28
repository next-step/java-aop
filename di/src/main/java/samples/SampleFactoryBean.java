package samples;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.context.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class SampleFactoryBean implements FactoryBean<SampleObject> {
    @Override
    public SampleObject getObject() {
        return new SampleObject("hello");
    }
}
