package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.Pointcut;
import org.easyspring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice{

    public AspectJAfterThrowingAdvice(Method adviceMethod,  AspectJExpressionPointcut pc,  AspectInstanceFactory adviceObject) {
        super(adviceMethod, pc, adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
           return methodInvocation.proceed();
        }catch (Throwable ex){
            invokeAdviceMethod();
            throw ex;
        }
    }
}
