package org.easyspring.beans.factory;

import org.easyspring.beans.NoSuchBeanDefinitionException;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:16
 */
public interface BeanFactory {


    Object getBean(String beanID);

    Class<?> getType(String targetBeanName) throws NoSuchBeanDefinitionException;
}
