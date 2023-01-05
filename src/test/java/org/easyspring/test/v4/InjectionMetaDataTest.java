package org.easyspring.test.v4;

import org.easyspring.beans.factory.annotation.AutowiredFieldElement;
import org.easyspring.beans.factory.annotation.InjectionElement;
import org.easyspring.beans.factory.annotation.InjectionMetaData;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.dao.v4.AccountDao;
import org.easyspring.dao.v4.ItemDao;
import org.easyspring.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;

public class InjectionMetaDataTest {

    @Test
    public void testInjection() throws Exception{
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v4.xml"));

        Class<PetStoreService> aClass = PetStoreService.class;
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();
        {
            Field f = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElement = new AutowiredFieldElement(f,true,factory);
            elements.add(injectionElement);
        }

        {
            Field f = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElement = new AutowiredFieldElement(f,true,factory);
            elements.add(injectionElement);
        }

        InjectionMetaData metaData = new InjectionMetaData(aClass, elements);

        PetStoreService petStore = new PetStoreService();
        metaData.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao );
    }
}
