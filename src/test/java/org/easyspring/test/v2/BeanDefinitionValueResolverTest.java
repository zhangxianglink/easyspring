package org.easyspring.test.v2;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypeStringValue;
import org.easyspring.beans.factory.context.support.BeanDefinitionValueResolver;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.dao.v2.AccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-16 16:46
 */
public class BeanDefinitionValueResolverTest {

     DefaultBeanFactory factory ;
     XmlBeanDefinitionReader reader;
    @Before
    public void init(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }


    @Test
    public void resolverTest(){

        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        final RuntimeBeanReference accountDao = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(accountDao);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void resolverTest2(){

        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);
        final TypeStringValue typeStringValue = new TypeStringValue("test");
        Object value = resolver.resolveValueIfNecessary(typeStringValue);

        Assert.assertNotNull(value);
        Assert.assertEquals("this is value",value);
    }
}
