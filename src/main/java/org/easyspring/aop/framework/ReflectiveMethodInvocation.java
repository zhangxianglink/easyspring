package org.easyspring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocation implements MethodInvocation {

    protected final Object targetObject;
    protected final Method targetMethod;

    protected Object[] arguments;

    protected final List<MethodInterceptor> interceptors;
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation( Object targetObject, Method targetMethod, Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetMethod =targetMethod;
        this.targetObject = targetObject;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    @Override
    public Method getMethod() {
        return this.targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments != null ? this.arguments : new Object[0];
    }

    @Override
    public Object proceed() throws Throwable {
        if (currentInterceptorIndex == this.interceptors.size() -1){
             return invokeJoinpoint();
        }
        this.currentInterceptorIndex ++;
        MethodInterceptor interceptor = this.interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    private Object invokeJoinpoint() throws Throwable{
        return this.targetMethod.invoke(this.targetObject,this.arguments);
    }

    @Override
    public Object getThis() {
        return this.targetObject;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return null;
    }
}
