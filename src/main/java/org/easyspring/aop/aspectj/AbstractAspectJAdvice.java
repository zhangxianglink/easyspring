package org.easyspring.aop.aspectj;

import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;
import org.easyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {


    private final Method adviceMethod;
    private final AspectJExpressionPointcut pc;
    private final AspectInstanceFactory aspectInstanceFactory;

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pc,  AspectInstanceFactory aspectInstanceFactory) {
        this.adviceMethod = adviceMethod;
        this.pc = pc;
        this.aspectInstanceFactory = aspectInstanceFactory;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }
    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceObject() {
        return aspectInstanceFactory;
    }

    public Object invokeAdviceMethod() throws Throwable{
        return adviceMethod.invoke(aspectInstanceFactory.getAspectInstance());
    }
}
