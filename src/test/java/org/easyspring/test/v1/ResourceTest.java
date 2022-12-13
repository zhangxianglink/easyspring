package org.easyspring.test.v1;

import org.easyspring.beans.factory.context.support.ClassPathXmlApplicationContext;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:11
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws IOException {
        Resource r = new ClassPathResource("petstore-v1.xml");
        InputStream is = null;
        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        }finally {
            if (is != null){
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {
        Resource r = new FileSystemResource("C:\\Users\\xiangzhang\\IdeaProjects\\easyspring\\src\\test\\resources\\petstore-v1.xml");
        InputStream is = null;
        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        }finally {
            if (is != null){
                is.close();
            }
        }
    }
}
