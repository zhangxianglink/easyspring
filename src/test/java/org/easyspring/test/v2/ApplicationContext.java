package org.easyspring.test.v2;

import org.easyspring.beans.factory.context.support.ClassPathXmlApplicationContext;
import org.easyspring.service.v2.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-16 11:31
 */
public class ApplicationContext {

    @Test
    public void getBean(){
        final ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petstore = (PetStoreService) cxt.getBean("petStore");

        Assert.assertNotNull(petstore);
        Assert.assertNotNull(petstore.getAccountDao());
        Assert.assertNotNull(petstore.getItemDao());
    }
}
