package org.easyspring.beans.support;

import org.easyspring.beans.BeanDefinition;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:58
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id ;
    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName(){
        return  this.beanClassName;
    }

}
