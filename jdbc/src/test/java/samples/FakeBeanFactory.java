package samples;

import com.interface21.beans.config.BeanPostProcessor;
import com.interface21.beans.factory.BeanFactory;
import com.interface21.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FakeBeanFactory implements BeanFactory {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public FakeBeanFactory() {
        beans.put(PlatformTransactionManager.class, new FakeTransactionManager());
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return Set.of();
    }

    @Override
    public <T> T getBean(final Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    @Override
    public void clear() {

    }

    @Override
    public void addBeanPostProcessor(final BeanPostProcessor beanPostProcessor) {

    }
}
