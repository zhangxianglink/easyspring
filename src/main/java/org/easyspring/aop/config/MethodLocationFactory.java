package org.easyspring.aop.config;

import org.easyspring.beans.BeanUtils;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.BeanFactoryAware;
import org.easyspring.beans.factory.FactoryBean;
import org.easyspring.util.StringUtils;

import java.lang.reflect.Method;

public class MethodLocationFactory implements FactoryBean<Method>, BeanFactoryAware {
    private String targetBeanName;
    private String methodName;
    private Method method;
    public void setTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        if (!StringUtils.hasText(this.targetBeanName)){
            throw new IllegalArgumentException("property 'targetBeanName' is required ");
        }
        if (!StringUtils.hasText(this.methodName)){
            throw new IllegalArgumentException("property 'methodName' is required");
        }
        Class<?> beanClass = beanFactory.getType(this.targetBeanName);
        if (beanClass == null){
            throw new IllegalArgumentException(" ");
        }
        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
        if (this.method == null){
            throw new IllegalArgumentException("");
        }
    }

    public Method getObject() {
        return this.method;
    }

    @Override
    public Class<?> getObjectType() {
        return Method.class ;
    }
}
