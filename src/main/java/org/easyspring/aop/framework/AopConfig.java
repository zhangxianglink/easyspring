package org.easyspring.aop.framework;

import org.easyspring.aop.Advice;
import org.easyspring.aop.aspectj.AbstractAspectJAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;

import java.lang.reflect.Method;
import java.util.List;

public interface AopConfig {
    void addAdvice(Advice advice);

    List<Advice> getAdvices();
    List<Advice> getAdvices(Method method);

    void setTargetObject(Object targetObject);

    Class<?> getTargetClass();
    Object getTargetObject();

    boolean isProxyTargetClass();
    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

}
