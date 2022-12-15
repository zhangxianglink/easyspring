package org.easyspring.beans.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.core.io.DefaultResourceLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangzhang
 * @since 2022-12-15 16:14
 */
public abstract class AbstractBeanFactory extends DefaultResourceLoader implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanID);
        if (bd == null){
            throw new BeanCreationException("bean definition does not exist");
        }
        return this.getBeanByDefinition(bd);

    }

    public Object createBean(BeanDefinition bd) {
        ClassLoader cl = this.getClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " is  error");
        }
    }

    @Override
    public void registryBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID,bd);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }


    protected abstract Object getBeanByDefinition(BeanDefinition bd );
}
