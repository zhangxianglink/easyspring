package org.easyspring.beans.support;

import org.easyspring.beans.BeanDefinition;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:16
 */
public class DefaultBeanFactory extends AbstractBeanFactory {


    public DefaultBeanFactory(){
        super();
    }

    @Override
    protected Object getBeanByDefinition(BeanDefinition bd) {
        return createBean(bd);
    }
}
