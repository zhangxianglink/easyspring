package org.easyspring.beans.factory.context.support;

import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypeStringValue;
import org.easyspring.beans.support.DefaultBeanFactory;

/**
 * @author xiangzhang
 * @since 2022-12-16 17:25
 */
public class BeanDefinitionValueResolver {
    private final BeanFactory factory;
    public BeanDefinitionValueResolver(BeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object obj) {
        if (obj instanceof RuntimeBeanReference){
            RuntimeBeanReference ref = (RuntimeBeanReference) obj;
            return factory.getBean(ref.getRefId());
        }else if (obj instanceof TypeStringValue){
            TypeStringValue value = (TypeStringValue) obj;
            return value.getValue();
        } else {
            throw new RuntimeException("the value "  + obj + " has not implemented ");
        }
    }
}
