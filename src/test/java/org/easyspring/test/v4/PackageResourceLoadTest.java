package org.easyspring.test.v4;

import org.easyspring.core.io.Resource;
import org.easyspring.core.io.support.PackageResourceLoader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author xiangzhang
 * @since 2022-12-27 16:59
 */
public class PackageResourceLoadTest {

    @Test
    public void test() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.easyspring.dao.v4");
        Assert.assertEquals(2,resources.length);
    }
}
