package org.easyspring.beans.factory.context.support;

import org.easyspring.beans.factory.context.ApplicationContext;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;

/**
 * @author xiangzhang
 * @since 2022-12-13 21:45
 */
public class FileSystemXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;
    public FileSystemXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource fileSystemResource = new FileSystemResource(configFile);
        reader.loadBeanDefinition(fileSystemResource);
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
