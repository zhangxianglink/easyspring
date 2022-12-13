package org.easyspring.beans.factory.context.support;

import org.easyspring.beans.factory.context.ApplicationContext;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;

/**
 * @author xiangzhang
 * @since 2022-12-11 20:59
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private   DefaultBeanFactory factory;
    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource classPathResource = new ClassPathResource(configFile);
        reader.loadBeanDefinition(classPathResource);
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
