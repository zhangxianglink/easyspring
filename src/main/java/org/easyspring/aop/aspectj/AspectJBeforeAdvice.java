package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;
import org.easyspring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod, Pointcut pc,  Object adviceObject) {
        super(adviceMethod,pc,adviceObject);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
       this.invokeAdviceMethod();
        return methodInvocation.proceed();
    }
}
