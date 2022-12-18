package org.easyspring.test.v2;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ProperTypeValue;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
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
        reader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));

        final BeanDefinition petStore = factory.getBeanDefinition("petStore");
        List<ProperTypeValue> list = petStore.getProperValues();

        Assert.assertTrue(list.size() == 3) ;

        {
            ProperTypeValue ptv = this.getPropertyValue("accountDao",list);
            Assert.assertNotNull(ptv);
            Assert.assertTrue(ptv.getValue() instanceof RuntimeBeanReference);
        }

        {
            ProperTypeValue ptv = this.getPropertyValue("itemDao",list);
            Assert.assertNotNull(ptv);
            Assert.assertTrue(ptv.getValue() instanceof RuntimeBeanReference);
        }
    }

    private ProperTypeValue getPropertyValue(String name, List<ProperTypeValue> list) {
        for (ProperTypeValue typeValue : list){
            if (name.equals(typeValue.getName())){
                return typeValue;
            }
        }
        return null;
    }
}
