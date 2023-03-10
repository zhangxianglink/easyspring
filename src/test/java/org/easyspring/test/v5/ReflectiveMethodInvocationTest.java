package org.easyspring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.aop.framework.ReflectiveMethodInvocation;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.service.v5.PetStoreService5;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectiveMethodInvocationTest extends AbstractV5Test{

    private PetStoreService5 petStoreService5;
    private TransactionManager tx;
    private AspectJBeforeAdvice aspectJBeforeAdvice;
    private AspectJAfterReturningAdvice aspectJAfterReturningAdvice;
    private AspectJAfterThrowingAdvice aspectJAfterThrowingAdvice;
    private BeanFactory beanFactory;
    private AspectInstanceFactory aspectInstanceFactory;

    @Before
    public void setUp() throws Exception{
        petStoreService5 = new PetStoreService5();
        tx = new TransactionManager();
        MessageTracker.clearMsgs();
        beanFactory = this.getBeanFactory("petstore-v5.xml");
        aspectInstanceFactory = this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        aspectJBeforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),null,aspectInstanceFactory);
        aspectJAfterReturningAdvice =  new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),null,aspectInstanceFactory);
        aspectJAfterThrowingAdvice =  new AspectJAfterThrowingAdvice(TransactionManager.class.getMethod("rollback"),null,aspectInstanceFactory);
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

    @Test
    public void testMethodInvocation2() throws Throwable{
        Method targetMethod = PetStoreService5.class.getDeclaredMethod("placeOrderException");
        ArrayList<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(aspectJAfterThrowingAdvice);
        interceptors.add(aspectJBeforeAdvice);

        ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(petStoreService5, targetMethod, new Object[0], interceptors);

        try {
            invocation.proceed();
        }catch (Throwable t){
            List<String> message = MessageTracker.getTrackerMessage();
            Assert.assertEquals(2 , message.size());
            Assert.assertEquals("start tx" , message.get(0));
            Assert.assertEquals("rollback tx" , message.get(1));
            return;
        }
       Assert.fail("no exception");
    }
}
