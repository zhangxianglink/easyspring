package org.easyspring.beans.factory.context.support;

import org.easyspring.beans.factory.context.ApplicationContext;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;

/**
 * @author xiangzhang
 * @since 2022-12-13 21:45
 */
public class FileSystemXmlApplicationContext extends abstractApplicationContext{


    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }

}