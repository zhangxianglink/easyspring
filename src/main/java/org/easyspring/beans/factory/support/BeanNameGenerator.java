package org.easyspring.beans.factory.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.support.BeanDefinitionRegistry;
import org.easyspring.context.annotation.ScannedGenericBeanDefinition;

public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition sbd, BeanDefinitionRegistry registry);
}
