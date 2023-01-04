package org.easyspring.test.v4;

import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;
import org.easyspring.dao.v4.AccountDao;
import org.easyspring.service.v4.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws Exception{
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinition(resource);

        Field field = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(field,true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}
