package org.easyspring.test.v5;

import org.easyspring.aop.aspectj.AspectJAfterReturningAdvice;
import org.easyspring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.aop.framework.AopConfig;
import org.easyspring.aop.framework.AopConfigSupport;
import org.easyspring.aop.framework.CglibProxyFactory;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.service.v5.PetStoreService5;
import org.easyspring.tx.TransactionManager;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CglibAopProxyTest extends AbstractV5Test{
    private PetStoreService5 petStoreService5;
    private TransactionManager tx;
    private AspectJBeforeAdvice aspectJBeforeAdvice;
    private AspectJAfterReturningAdvice aspectJAfterReturningAdvice;
    private AspectJExpressionPointcut pc ;
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

        pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* org.easyspring.service.v5.*.placeOrder(..))");
        aspectJBeforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"),pc,aspectInstanceFactory);
        aspectJAfterReturningAdvice =  new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"),pc,aspectInstanceFactory);
    }

    @Test
    public void testGetProxy() {
         AopConfig config = new AopConfigSupport();
         config.addAdvice(aspectJBeforeAdvice);
         config.addAdvice(aspectJAfterReturningAdvice);
         config.setTargetObject(petStoreService5);

         CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
         PetStoreService5 psr = (PetStoreService5) proxyFactory.getProxy();
         psr.placeOrder();

        List<String> message = MessageTracker.getTrackerMessage();
        Assert.assertEquals(3 , message.size());
        Assert.assertEquals("start tx" , message.get(0));
        Assert.assertEquals("commit tx" , message.get(2));
        Assert.assertEquals("place order" , message.get(1));

    }
}
