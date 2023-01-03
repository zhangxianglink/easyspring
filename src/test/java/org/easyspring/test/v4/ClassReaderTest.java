package org.easyspring.test.v4;


import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.type.classreading.AnnotationMetaDataReadingVisitor;
import org.easyspring.core.type.classreading.CLassMetadataReadingVisitor;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * @author xiangzhang
 * @since 2022-12-28 16:28
 */
public class ClassReaderTest {

    @Test
    public void testGetCLassMetaData() throws IOException {
        final ClassPathResource resource = new ClassPathResource("org/easyspring/service/v4/PetStoreService.class");
        final ClassReader reader = new ClassReader(resource.getInputStream());

        CLassMetadataReadingVisitor visitor = new CLassMetadataReadingVisitor();
        reader.accept(visitor,ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.easyspring.service.v4.PetStoreService",visitor.getClassName());
        Assert.assertEquals("java.lang.Object",visitor.getSuperClassName());
        Assert.assertEquals(0,visitor.getInterfaceNames().length);

    }

     @Test
    public void testGetAnnotation() throws Exception{
         final ClassPathResource resource = new ClassPathResource("org/easyspring/service/v4/PetStoreService.class");
         final ClassReader reader = new ClassReader(resource.getInputStream());

         AnnotationMetaDataReadingVisitor visitor = new AnnotationMetaDataReadingVisitor();
         reader.accept(visitor, ClassReader.SKIP_DEBUG);

         String annotation = "org.easyspring.stereotype.Component";
         Assert.assertTrue(visitor.hasAnnotation(annotation));

         AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);
         Assert.assertEquals("petStore",attributes.get("value"));
     }
}
