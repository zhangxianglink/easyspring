package org.easyspring.beans.factory.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.SimpleTypeConvert;
import org.easyspring.beans.TypeMisMatchException;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.support.AbstractBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author xiangzhang
 * @since 2022-12-20 23:01
 * 找到一个合适的构造函数创建对象
 */
public class ConstructorResolver {
    private final Log logger = LogFactory.getLog(getClass());

    private final AbstractBeanFactory beanFactory;

    public ConstructorResolver(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    public Object autowireConstructor(BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass;
        try {
            beanClass = bd.getBeanClass();
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException("can't constructor");
        }

        final Constructor<?>[] constructors = beanClass.getConstructors();

        final BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this.beanFactory);
        final SimpleTypeConvert convert = new SimpleTypeConvert();
        final ConstructorArgument cargs = bd.getConstructorArgument();

        for (int i = 0; i < constructors.length; i++) {
            final Class<?>[] parameterTypes = constructors[i].getParameterTypes();
            if (parameterTypes.length != cargs.getArgumentValueCount()){
                continue;
            }
            argsToUse = new Object[parameterTypes.length];
            boolean result = this.valuesMatchTypes(parameterTypes,cargs.getArgumentValues(),argsToUse,resolver,convert);
            if (result){
                constructorToUse = constructors[i];
                break;
            }
        }
        if (constructorToUse == null){
            throw new BeanCreationException("can't constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException("can't constructor");
        }

    }

    private boolean valuesMatchTypes(Class<?>[] parameterTypes, List<ConstructorArgument.ValueHolder> argumentValues, Object[] argsToUse, BeanDefinitionValueResolver resolver, SimpleTypeConvert convert) {
        for (int i = 0; i < parameterTypes.length; i++) {
            final ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            final Object value = valueHolder.getValue();

            final Object resolveValue = resolver.resolveValueIfNecessary(value);
            try {
                final Object convertValue= convert.convertIfNecessary(resolveValue, parameterTypes[i]);
                argsToUse[i] = convertValue;
            } catch (TypeMisMatchException e) {
                logger.error("TypeMisMatchException ");
                return false;
            }
        }
        return true;
    }
}
