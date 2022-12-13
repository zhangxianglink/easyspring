package org.easyspring.beans.factory.context.support;


import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;

/**
 * @author xiangzhang
 * @since 2022-12-11 20:59
 */
public class ClassPathXmlApplicationContext extends abstractApplicationContext{


    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);
    }
}
