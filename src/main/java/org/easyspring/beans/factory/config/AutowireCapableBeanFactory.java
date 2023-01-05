package org.easyspring.beans.factory.config;

import org.easyspring.beans.factory.BeanFactory;

import java.util.List;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);

    void addBeanPostProcessor(BeanPostProcessor postProcessor);
    List<BeanPostProcessor> getBeanPostProcessor();
}
