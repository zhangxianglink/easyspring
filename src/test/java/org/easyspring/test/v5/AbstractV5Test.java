package org.easyspring.test.v5;

import org.easyspring.aop.config.AspectInstanceFactory;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.io.ClassPathResource;
import org.easyspring.core.io.Resource;
import org.easyspring.tx.TransactionManager;

import java.lang.reflect.Method;

public class AbstractV5Test {
		
	protected BeanFactory getBeanFactory(String configFile){
		DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultBeanFactory);
		Resource resource = new ClassPathResource(configFile);
		reader.loadBeanDefinition(resource);
		return  defaultBeanFactory;		
	}
	
	protected  Method getAdviceMethod( String methodName) throws Exception{
		return TransactionManager.class.getMethod(methodName);		
	}
	
	protected  AspectInstanceFactory getAspectInstanceFactory(String targetBeanName){
		AspectInstanceFactory factory = new AspectInstanceFactory();
		factory.setAspectBeanName(targetBeanName);		
		return factory;
	}
	
	
}
