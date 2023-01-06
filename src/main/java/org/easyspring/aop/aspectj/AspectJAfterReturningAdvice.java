package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;

import java.lang.reflect.Method;

public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice{

    public AspectJAfterReturningAdvice(Method adviceMethod, Pointcut pc,  Object adviceObject) {
        super(adviceMethod,pc,adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object proceed = methodInvocation.proceed();
        this.invokeAdviceMethod();
        return proceed;
    }
}
