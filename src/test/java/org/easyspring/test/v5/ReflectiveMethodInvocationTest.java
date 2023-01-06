package org.easyspring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.framework.ReflectiveMethodInvocation;
import org.easyspring.service.v5.PetStoreService5;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocationTest {

    private PetStoreService5 petStoreService5;
    private TransactionManager tx;
    private AspectJBeforeAdvice aspectJBeforeAdvice;
    private AspectJAfterReturningAdvice aspectJAfterReturningAdvice;

    @Before
    public void setUp() throws Exception{
        petStoreService5 = new PetStoreService5();
        tx = new TransactionManager();
        MessageTracker.clearMsgs();
        aspectJBeforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),null,tx);
        aspectJAfterReturningAdvice =  new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),null,tx);
    }

    @Test
    public void testMethodInvocation() throws Throwable{
        Method targetMethod = PetStoreService5.class.getMethod("placeOrder");
        ArrayList<MethodInterceptor>  interceptors = new ArrayList<>();
        interceptors.add(aspectJAfterReturningAdvice);
        interceptors.add(aspectJBeforeAdvice);

        ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(petStoreService5, targetMethod, new Object[0], interceptors);
        invocation.proceed();

        List<String> message = MessageTracker.getTrackerMessage();
        Assert.assertEquals(3 , message.size());
        Assert.assertEquals("start tx" , message.get(0));
        Assert.assertEquals("commit tx" , message.get(2));
        Assert.assertEquals("place order" , message.get(1));
    }
}
