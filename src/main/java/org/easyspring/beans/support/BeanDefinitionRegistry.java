package org.easyspring.beans.support;

import org.easyspring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registryBeanDefinition(String beanID, BeanDefinition bd);
    BeanDefinition getBeanDefinition(String beanID);
}
