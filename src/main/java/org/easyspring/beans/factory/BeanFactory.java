package org.easyspring.beans.factory;

import org.easyspring.beans.BeanDefinition;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:16
 */
public interface BeanFactory {

    BeanDefinition getBeanDefinition(String beanID);

    Object getBean(String beanID);
}
