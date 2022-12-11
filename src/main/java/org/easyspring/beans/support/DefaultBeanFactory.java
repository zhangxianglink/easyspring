package org.easyspring.beans.support;

import jdk.internal.util.xml.impl.Input;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.BeansException;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangzhang
 * @since 2022-12-11 14:16
 */
public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    /**
     * 解析xml
     * @param configFile
     */
    public DefaultBeanFactory(String configFile){
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        try (InputStream is = cl.getResourceAsStream(configFile)){
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement(); //<beans>
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                this.beanDefinitionMap.put(id,bd);
            }
        }catch (Exception e) {
            throw new BeanDefinitionStoreException("definition bean for " + configFile + " is  error");
        }

    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanID);
        if (bd == null){
            throw new BeanCreationException("bean definition does not exist");
        }
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
             Class<?> clz = cl.loadClass(beanClassName);
             return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " is  error");
        }
    }
}
