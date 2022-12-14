package org.easyspring.beans.factory.context.support;

import org.easyspring.beans.factory.context.ApplicationContext;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.DefaultResourceLoader;
import org.easyspring.core.io.Resource;
import org.easyspring.util.ClassUtils;

public abstract class abstractApplicationContext extends DefaultResourceLoader implements ApplicationContext {

    private DefaultBeanFactory factory;

    public abstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        factory.setClassLoader(this.getClassLoader());
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource classPathResource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(classPathResource);
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }


    protected abstract Resource getResourceByPath(String path);
}
