package org.easyspring.beans.support;

import org.easyspring.aop.config.MethodLocationFactory;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.ProperTypeValue;
import org.easyspring.core.io.DefaultResourceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:58
 */
public class GenericBeanDefinition extends DefaultResourceLoader implements BeanDefinition {

    private String id ;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;

    public GenericBeanDefinition(Class<?> clz) {
        this.beanClass =clz;
        this.beanClassName = clz.getName();
    }

    public void setSynthetic(boolean synthetic) {
        isSynthetic = synthetic;
    }

    private boolean isSynthetic = false;


    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition() {

    }

    @Override
    public String getBeanClassName(){
        return  this.beanClassName;
    }

    @Override
    public String getBeanId() {
        return this.id;
    }

    @Override
    public boolean isSingleton() {
        return this.singleton;
    }

    @Override
    public boolean isPrototype() {
        return this.prototype;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }
    public void setBeanId(String id) {
        this.id = id;
    }


    private List<ProperTypeValue> properValues = new ArrayList<>();

    @Override
    public List<ProperTypeValue> getProperValues() {
        return this.properValues;
    }

    private ConstructorArgument constructorArguments = new ConstructorArgument();

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArguments;
    }

    @Override
    public boolean hasConstructorArgument() {
        return !this.constructorArguments.getArgumentValues().isEmpty();
    }

    private Class<?> beanClass;

    @Override
    public Class<?> getBeanClass() {
        try {
            return this.beanClass == null ? getClassLoader().loadClass(this.beanClassName) : this.beanClass;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> resolveBeanClass() throws ClassNotFoundException {
        String className = getBeanClassName();
        if (Objects.isNull(className)){
            return null;
        }
        Class<?> resolvedClass = getClassLoader().loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }

    @Override
    public boolean isSynthetic() {
        return this.isSynthetic;
    }


}
