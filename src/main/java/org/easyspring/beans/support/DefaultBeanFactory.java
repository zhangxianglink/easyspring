package org.easyspring.beans.support;

import com.sun.istack.internal.NotNull;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanFactory;

import org.easyspring.core.io.DefaultResourceLoader;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:16
 */
public class DefaultBeanFactory extends DefaultResourceLoader implements BeanFactory , BeanDefinitionRegistry {


    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();


    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanID);
        if (bd == null){
            throw new BeanCreationException("bean definition does not exist");
        }
        if (bd.isSingleton()){
            Object singleton = this.getSingleton(beanID);
            if(singleton == null){
                singleton = createBean(bd);
                this.registerSingleton(beanID,singleton);
            }
            return singleton;
        }
        return createBean(bd);
    }

    private Object createBean(BeanDefinition bd) {
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



}
