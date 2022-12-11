package org.easyspring.test.v1;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:08
 */
public class BeanFactoryTest {

    @Test
    public void testGetBean(){
        BeanFactory beanFactory = new DefaultBeanFactory("petstore-v1.xml");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");
        Assert.assertEquals("org.easyspring.service.v1.PetStoreService",beanDefinition.getBeanClassName());
        PetStoreService petStore = (PetStoreService) beanFactory.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean(){
        final DefaultBeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        try {
            factory.getBean("nva");
        }catch (BeanCreationException e){
            return;
        }
        Assert.fail("exception BeanCreationException");
    }

    @Test
    public void testDefinition(){
        try {
            new DefaultBeanFactory("test.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("exception BeanDefinitionStoreException");
    }
}
