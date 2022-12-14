package org.easyspring.test.v1;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.service.v1.PetStoreService;
import org.easyspring.service.v1.proBeanService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:08
 */
public class BeanFactoryTest {

    DefaultBeanFactory beanFactory= null;
    XmlBeanDefinitionReader xmlReader = null;

    @Before
    public void setUp(){
         beanFactory = new DefaultBeanFactory();
         xmlReader = new XmlBeanDefinitionReader(beanFactory);
    }

    @Test
    public void testGetBean(){
        xmlReader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");

        Assert.assertTrue(beanDefinition.isSingleton());
        Assert.assertFalse(beanDefinition.isPrototype());
        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT,beanDefinition.getScope());

        Assert.assertEquals("org.easyspring.service.v1.PetStoreService",beanDefinition.getBeanClassName());
        PetStoreService petStore = (PetStoreService) beanFactory.getBean("petStore");
        Assert.assertNotNull(petStore);

        Assert.assertEquals("org.easyspring.service.v1.PetStoreService",beanDefinition.getBeanClassName());
        PetStoreService petStore1 = (PetStoreService) beanFactory.getBean("petStore");

        Assert.assertEquals(petStore1,petStore);
    }

    @Test
    public void testPrototypeBean(){
        xmlReader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        final proBeanService proBean = (proBeanService) beanFactory.getBean("proBean");
        final BeanDefinition bd = beanFactory.getBeanDefinition("proBean");
        Assert.assertTrue(bd.isPrototype());

        xmlReader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
        final proBeanService proBean2 = (proBeanService) beanFactory.getBean("proBean");

        Assert.assertNotEquals(proBean,proBean2);
    }

    @Test
    public void testInvalidBean(){
        try {
            xmlReader.loadBeanDefinition(new ClassPathResource("petstore-v1.xml"));
            beanFactory.getBean("nva");
        }catch (BeanCreationException e){
            return;
        }
        Assert.fail("exception BeanCreationException");
    }

    @Test
    public void testDefinition(){
        try {
            xmlReader.loadBeanDefinition(new ClassPathResource("test-v1.xml"));
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("exception BeanDefinitionStoreException");
    }
}
