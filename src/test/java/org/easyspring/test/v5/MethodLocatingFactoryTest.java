package org.easyspring.test.v5;

import org.easyspring.aop.config.MethodLocationFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.tx.TransactionManager;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class MethodLocatingFactoryTest {

    @Test
    public void testGetMethod() throws  Exception{
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v5.xml"));

        MethodLocationFactory factory = new MethodLocationFactory();
        factory.setTargetBeanName("tx");
        factory.setMethodName("start");
        factory.setBeanFactory(beanFactory);

        Method m = factory.getObject();
        Assert.assertTrue(TransactionManager.class.equals(m.getDeclaringClass()));
        Assert.assertTrue(m.equals(TransactionManager.class.getMethod("start")));
    }
}
