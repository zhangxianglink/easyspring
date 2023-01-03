package org.easyspring.test.v4;

import org.easyspring.beans.factory.context.support.ClassPathXmlApplicationContext;
import org.easyspring.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-27 15:28
 */
public class applicationContextTest4 {

    @Test
    public void testGetBeanProperty(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStoreService = (PetStoreService) context.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
