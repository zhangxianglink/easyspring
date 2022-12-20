package org.easyspring.test.v3;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.ProperTypeValue;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypeStringValue;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.SingletonBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author xiangzhang
 * @since 2022-12-16 11:51
 */
public class BeanDefinitionTest {

    @Test
    public void testBeanDefinition(){
        final SingletonBeanFactory factory = new SingletonBeanFactory();
        final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v3.xml"));

        final BeanDefinition petStore = factory.getBeanDefinition("petStore");
        Assert.assertEquals("org.easyspring.service.v2.PetStoreService",petStore.getBeanClassName());

        ConstructorArgument constructorArgument = petStore.getConstructorArgument();

        Assert.assertNotNull(constructorArgument);

        List<ConstructorArgument.ValueHolder> valueHolders = constructorArgument.getArgumentValues();
        Assert.assertTrue(3 == valueHolders.size());


        RuntimeBeanReference reference1 = (RuntimeBeanReference) valueHolders.get(0).getValue();
        Assert.assertEquals("accountDao",reference1.getRefId());

        RuntimeBeanReference reference2 = (RuntimeBeanReference) valueHolders.get(1).getValue();
        Assert.assertEquals("itemDao",reference2.getRefId());

        TypeStringValue reference3 = (TypeStringValue) valueHolders.get(2).getValue();
        Assert.assertEquals("1",reference3.getValue());

    }
}
