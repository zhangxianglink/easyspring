package org.easyspring.beans;

import java.util.List;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:18
 */
public interface BeanDefinition {

    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    String getBeanClassName();

    String getBeanId();

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    List<ProperTypeValue>  getProperValues();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgument();
}
