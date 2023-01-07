package org.easyspring.aop.config;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.BeanFactoryAware;

public class AspectInstanceFactory implements BeanFactoryAware {
    private String aspectBeanName;
    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object getAspectInstance() throws Exception {
        return this.beanFactory.getBean(this.aspectBeanName);
    }
}
