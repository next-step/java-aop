package samples;

import com.interface21.beans.BeanInstantiationException;
import com.interface21.beans.factory.FactoryBean;
import com.interface21.beans.factory.support.BeanFactoryUtils;
import com.interface21.context.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
@SuppressWarnings("unused")
public class SampleFactoryBean implements FactoryBean<SampleObject> {
    @Override
    public SampleObject getObject() {
        return new SampleObject("hello");
    }

    @Override
    public SampleObject getObject(Class<?>[] parameterTypes, Object[] objects) {
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(SampleObject.class);
        try {
            return (SampleObject) injectedConstructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanInstantiationException(SampleObject.class,  "Cannot instantiate bean");
        }
    }

    @Override
    public Class<?> getObjectType() {
        return SampleObject.class;
    }
}
