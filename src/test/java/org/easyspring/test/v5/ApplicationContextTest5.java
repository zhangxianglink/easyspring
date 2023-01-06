package org.easyspring.test.v5;

import org.easyspring.beans.factory.context.support.ClassPathXmlApplicationContext;
import org.easyspring.service.v5.PetStoreService5;
import org.easyspring.util.MessageTracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ApplicationContextTest5 {

    @Before
    public void setUp(){
        MessageTracker.clearMsgs();
    }
    @Test
    public void testPlaceOrder() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("petstore-v5.xml");
        PetStoreService5 petStoreService5 = (PetStoreService5) context.getBean("petStore");
        Assert.assertNotNull(petStoreService5.getAccountDao());
        Assert.assertNotNull(petStoreService5.getItemDao());

        petStoreService5.placeOrder();

        List<String> message = MessageTracker.getTrackerMessage();
        Assert.assertEquals(3 , message.size());
        Assert.assertEquals("start tx" , message.get(0));
        Assert.assertEquals("commit tx" , message.get(2));
        Assert.assertEquals("place order" , message.get(1));
    }
}
