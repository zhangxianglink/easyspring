package org.easyspring.test.v4;

import org.easyspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.easyspring.beans.factory.annotation.AutowiredFieldElement;
import org.easyspring.beans.factory.annotation.InjectionElement;
import org.easyspring.beans.factory.annotation.InjectionMetaData;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.dao.v4.AccountDao;
import org.easyspring.dao.v4.ItemDao;
import org.easyspring.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;

public class AutowiredAnnotationProcessorTest {
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();

    DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory(){
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if (descriptor.getDependencyType().equals(AccountDao.class)){
                return accountDao;
            }
            if (descriptor.getDependencyType().equals(ItemDao.class)){
                return itemDao;
            }
            throw new RuntimeException("not support this descriptor");
        }
    };


    @Test
    public void testGetInjectionMetaData(){
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(defaultBeanFactory);
        InjectionMetaData metaData = processor.buildAutowiredMetadata(PetStoreService.class);
        LinkedList<InjectionElement> injectionElements = metaData.getInjectionElements();

        Assert.assertEquals(2, injectionElements.size());
        assertFieldExits(injectionElements, "accountDao");
        assertFieldExits(injectionElements, "itemDao");


        PetStoreService petStore = new PetStoreService();
        metaData.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao );
    }

    private void assertFieldExits(LinkedList<InjectionElement> injectionElements, String fieldName) {
        for (InjectionElement injectionElement : injectionElements){
            AutowiredFieldElement ele = (AutowiredFieldElement) injectionElement;
            String name = ele.getField().getName();
            if (name.equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + " not found");
    }
}
