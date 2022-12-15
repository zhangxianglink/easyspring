package org.easyspring.beans.support;

import com.sun.istack.internal.NotNull;
import org.easyspring.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangzhang
 * @since 2022-12-15 16:42
 */
public class SingletonBeanFactory  extends AbstractBeanFactory implements SingletonBeanRegistry {

    public SingletonBeanFactory() {
        super();
    }

    private final Map<String,Object> singletonMap = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(@NotNull String beanID, Object bean) {
        Object singleton = getSingleton(beanID);
        if (singleton != null){
            throw new IllegalStateException("Could not register: " + bean + " under bean name: " + beanID + " bean is already: " + singleton);
        }
        this.singletonMap.put(beanID,bean);
    }

    @Override
    public Object getSingleton(String beanID) {
        return this.singletonMap.get(beanID);
    }

    @Override
    protected Object getBeanByDefinition(BeanDefinition bd) {
        if (bd.isSingleton()){
            Object singleton = this.getSingleton(bd.getBeanId());
            if(singleton == null){
                singleton = createBean(bd);
                this.registerSingleton(bd.getBeanId(),singleton);
            }
            return singleton;
        }
        return createBean(bd);
    }
}
