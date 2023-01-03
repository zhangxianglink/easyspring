package org.easyspring.test.v4;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.classreading.MetaDataReader;
import org.easyspring.core.type.classreading.SimpleMetaDataReader;
import org.easyspring.stereotype.Component;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class MetaDataReaderTest {

    @Test
    public void testGetMetaData() throws IOException{
        final ClassPathResource resource = new ClassPathResource("org/easyspring/service/v4/PetStoreService.class");
        MetaDataReader reader = new SimpleMetaDataReader(resource);
        AnnotationMetaData amd = reader.getAnnotationMetaData();
        String name = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(name));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(name);
        Assert.assertEquals("petStore",attributes.get("value"));


        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isInterface());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("org.easyspring.service.v4.PetStoreService",amd.getClassName());
        Assert.assertEquals("java.lang.Object",amd.getSuperClassName());
        Assert.assertEquals(0,amd.getInterfaceNames().length);
    }
}
