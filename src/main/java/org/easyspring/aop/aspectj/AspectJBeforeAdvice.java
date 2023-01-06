package org.easyspring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.easyspring.aop.Advice;
import org.easyspring.aop.Pointcut;
import org.easyspring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AspectJBeforeAdvice implements Advice {
    private final Method adviceMethod;
    private final Pointcut pc;
    private final Object adviceObject;
    public AspectJBeforeAdvice(Method adviceMethod, Pointcut pc,  Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pc = pc;
        this.adviceObject = adviceObject;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        adviceMethod.invoke(adviceObject);
        return methodInvocation.proceed();
    }
}
