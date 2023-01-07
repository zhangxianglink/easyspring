package org.easyspring.test.v5;

import org.easyspring.aop.aspectj.AspectJBeforeAdvice;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.aop.config.MethodLocationFactory;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.ProperTypeValue;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BeanDefinitionTestV5 extends AbstractV5Test{

    @Test
    public void testAopBean(){
        DefaultBeanFactory factory = (DefaultBeanFactory) this.getBeanFactory("petstore-v5.xml");
        {
            BeanDefinition tx = factory.getBeanDefinition("tx");
            Assert.assertTrue(tx.getBeanClassName().equals(TransactionManager.class.getName()));
        }

        {
            BeanDefinition bd = factory.getBeanDefinition("placeOrder");
            // 合成
            Assert.assertTrue(bd.isSynthetic());
            Assert.assertTrue(bd.getBeanClass().equals(AspectJExpressionPointcut.class));

            ProperTypeValue pv = bd.getProperValues().get(0);
            Assert.assertEquals("expression",pv.getName());
            Assert.assertEquals("execution(* org.easyspring.service.v5.*.placeOrder(..))",pv.getValue());
        }

        {
            String name = AspectJBeforeAdvice.class.getName() + "#0";
            BeanDefinition bd = factory.getBeanDefinition(name);
            Assert.assertTrue(bd.getBeanClass().equals(AspectJBeforeAdvice.class));
            // 合成
            Assert.assertTrue(bd.isSynthetic());

            List<ConstructorArgument.ValueHolder> args = bd.getConstructorArgument().getArgumentValues();
            Assert.assertTrue(3 == args.size());


            //构造函数第一个参数
            {
                BeanDefinition innerBeanDef = (BeanDefinition) args.get(0).getValue();
                Assert.assertTrue(innerBeanDef.isSynthetic());
                Assert.assertTrue(innerBeanDef.getBeanClass().equals(MethodLocationFactory.class));

                List<ProperTypeValue> pvs = innerBeanDef.getProperValues();
                Assert.assertEquals("targetBeanName", pvs.get(0).getName());
                Assert.assertEquals("tx", pvs.get(0).getValue());
                Assert.assertEquals("methodName", pvs.get(1).getName());
                Assert.assertEquals("start", pvs.get(1).getValue());
            }

            //构造函数第二个参数
            {
                RuntimeBeanReference ref = (RuntimeBeanReference) args.get(1).getValue();
                Assert.assertEquals("placeOrder", ref.getRefId());
            }

            //构造函数第三个参数
            {
                BeanDefinition innerBeanDef = (BeanDefinition) args.get(2).getValue();
                Assert.assertTrue(innerBeanDef.isSynthetic());
                Assert.assertTrue(innerBeanDef.getBeanClass().equals(AspectInstanceFactory.class));

                List<ProperTypeValue> pvs = innerBeanDef.getProperValues();
                Assert.assertEquals("aspectBeanName", pvs.get(0).getName());
                Assert.assertEquals("tx", pvs.get(0).getValue());

            }
        }
    }
}
