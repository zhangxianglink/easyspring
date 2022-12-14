package org.easyspring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.support.BeanDefinitionRegistry;
import org.easyspring.beans.support.GenericBeanDefinition;
import org.easyspring.core.io.Resource;
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
    private static final String SCOPE_ATTRIBUTE = "scope";

    BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void loadBeanDefinition(Resource resource) {
        try (InputStream is = resource.getInputStream()){
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement(); //<beans>
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = iter.next();
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                String scope = ele.attributeValue(SCOPE_ATTRIBUTE);
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
                if (scope != null) {
                    bd.setScope(scope);
                }
                this.registry.registryBeanDefinition(id,bd);
            }
        }catch (Exception e) {
            throw new BeanDefinitionStoreException("definition bean for " + resource.getDescription() + " is  error");
        }

    }
}
