package org.easyspring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.support.BeanDefinitionRegistry;
import org.easyspring.beans.support.GenericBeanDefinition;
import org.easyspring.util.ClassUtils;

import java.io.InputStream;
import java.util.Iterator;

/**
 * @author xiangzhang
 * @since 2022-12-11 18:33
 */
public class XmlBeanDefinitionReader {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";

    BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void loadBeanDefinition(String configFile) {
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
                this.registry.registryBeanDefinition(id,bd);
            }
        }catch (Exception e) {
            throw new BeanDefinitionStoreException("definition bean for " + configFile + " is  error");
        }

    }
}
