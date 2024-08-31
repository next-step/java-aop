package samples;

import com.interface21.beans.factory.FactoryBean;
import com.interface21.context.stereotype.Component;

@Component
public class ToolFactory implements FactoryBean<Tool> {

    @Override
    public Tool getObject() {
        return new Tool(1);
    }

    @Override
    public Class<Tool> getObjectType() {
        return Tool.class;
    }
}
