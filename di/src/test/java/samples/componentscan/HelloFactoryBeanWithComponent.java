package samples.componentscan;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.context.stereotype.Component;
import samples.Hello;

@Component
public class HelloFactoryBeanWithComponent implements FactoryBean<Hello> {
    @Override
    public Hello getObject() throws Exception {
        return Hello.from("park");
    }
}
