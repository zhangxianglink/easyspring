package org.easyspring.beans;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:18
 */
public interface BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";

    String getBeanClassName();

    String getBeanId();

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);
}
