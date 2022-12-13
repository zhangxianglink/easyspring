package org.easyspring.test.v1;

import org.easyspring.beans.factory.context.ApplicationContext;
import org.easyspring.beans.factory.context.support.ClassPathXmlApplicationContext;
import org.easyspring.beans.factory.context.support.FileSystemXmlApplicationContext;
import org.easyspring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-11 19:16
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void testGetBean2(){
        ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\Users\\xiangzhang\\IdeaProjects\\easyspring\\src\\test\\resources\\petstore-v1.xml");
        PetStoreService petStoreService = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }
}
