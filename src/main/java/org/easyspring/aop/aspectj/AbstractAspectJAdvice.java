package org.easyspring.aop.aspectj;

import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractAspectJAdvice implements Advice {


    private final Method adviceMethod;
    private final Pointcut pc;
    private final Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod, Pointcut pc,  Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pc = pc;
        this.adviceObject = adviceObject;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }
    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceObject() {
        return adviceObject;
    }

    public Object invokeAdviceMethod() throws Throwable{
        return adviceMethod.invoke(adviceObject);
    }
}
