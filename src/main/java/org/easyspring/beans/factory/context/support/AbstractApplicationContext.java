package org.easyspring.beans.factory.context.support;

import org.easyspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.easyspring.beans.factory.context.ApplicationContext;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.AbstractBeanFactory;
import org.easyspring.beans.support.SingletonBeanFactory;
import org.easyspring.core.io.DefaultResourceLoader;
import org.easyspring.core.io.Resource;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ApplicationContext {

    private SingletonBeanFactory factory;

    public AbstractApplicationContext(String configFile) {
        factory = new SingletonBeanFactory();
        factory.setClassLoader(this.getClassLoader());
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource classPathResource = this.getResourceByPath(configFile);
        reader.loadBeanDefinition(classPathResource);
        registerBeanPostProcessors(factory);
    }

    @Override
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected void registerBeanPostProcessors(AbstractBeanFactory factory) {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(factory);
        factory.addBeanPostProcessor(processor);
    }


    protected abstract Resource getResourceByPath(String path);
}
