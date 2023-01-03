package org.easyspring.test.v4;

import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.context.annotation.ClassPathBeanDefinitionScanner;
import org.easyspring.context.annotation.ScannedGenericBeanDefinition;
import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ClassPathBeanDefinitionScannerTest {

    @Test
    public void testParseScanBean() throws IOException {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        String basePackage = "org.easyspring.dao.v4,org.easyspring.service.v4";
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackage);

        String annotation = Component.class.getName();

        {
            BeanDefinition petStore = factory.getBeanDefinition("petStore");
            Assert.assertTrue(petStore instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) petStore;
            AnnotationMetaData metaData = sbd.getMetaData();

            Assert.assertTrue(metaData.hasAnnotation(annotation));
            AnnotationAttributes attributes = metaData.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore",attributes.get("value"));
        }

        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetaData metaData = sbd.getMetaData();
            Assert.assertTrue(metaData.hasAnnotation(annotation));
        }

        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetaData metaData = sbd.getMetaData();
            Assert.assertTrue(metaData.hasAnnotation(annotation));
        }

    }
}
