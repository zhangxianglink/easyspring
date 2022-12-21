package org.easyspring.beans.support;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.ProperTypeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:58
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id ;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private String scope = SCOPE_DEFAULT;


    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
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
    public String getId() {
        return this.id;
    }

}
