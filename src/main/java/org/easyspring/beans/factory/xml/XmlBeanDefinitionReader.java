package org.easyspring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.easyspring.aop.config.ConfigBeanDefinitionParser;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.ConstructorArgument;
import org.easyspring.beans.ProperTypeValue;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.config.RuntimeBeanReference;
import org.easyspring.beans.factory.config.TypeStringValue;
import org.easyspring.beans.support.BeanDefinitionRegistry;
import org.easyspring.beans.support.GenericBeanDefinition;
import org.easyspring.context.annotation.ClassPathBeanDefinitionScanner;
import org.easyspring.core.io.Resource;
import org.easyspring.util.StringUtils;

import java.io.IOException;
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

    private static final String PROPERTY_ELEMENT = "property";
    private static final String REF_ATTRIBUTE = "ref";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String NAME_ATTRIBUTE = "name";

    private static final String CONSTRUCTOR_ARG_ATTRIBUTE = "constructor-arg";

    public static final String BASE_PACKAGE_ATTRIBUTE  = "base-package";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";


    protected final Log logger = LogFactory.getLog(XmlBeanDefinitionReader.class);

    BeanDefinitionRegistry registry;
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;

    }

    public void loadBeanDefinition(Resource resource) {
        // try-with-resources ??????try??????????????????
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement(); //<beans>
            Iterator<Element> iter = root.elementIterator();
            while(iter.hasNext()){
                Element ele = iter.next();
                String namespaceURI = ele.getNamespaceURI();
                if(this.isDefaultNameSpace(namespaceURI)){
                    parseDefaultElements(ele);
                }else if (this.isContentNameSpace(namespaceURI)){
                    parseComponentElement(ele);
                }else {
                    // aop??????
                    parseAopElement(ele);
                }
            }
        }catch (Exception e) {
            throw new BeanDefinitionStoreException("definition bean for " + resource.getDescription() + " is  error");
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseAopElement(Element ele) {
        ConfigBeanDefinitionParser parser = new ConfigBeanDefinitionParser();
        parser.parse(ele, this.registry);
    }

    private void parseComponentElement(Element ele) {
        String  basePackage = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackage);
    }

    private void parseDefaultElements(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
        String scope = ele.attributeValue(SCOPE_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);
        if (scope != null) {
            bd.setScope(scope);
        }
        parseConstructorElements(ele,bd);
        parsePropertyElement(ele,bd);
        this.registry.registryBeanDefinition(id,bd);
    }

    public boolean isDefaultNameSpace(String nameSpaceUri) {
        return !StringUtils.hasLength(nameSpaceUri) || BEANS_NAMESPACE_URI.equals(nameSpaceUri);
    }

    public boolean isContentNameSpace(String nameSpaceUri) {
        return !StringUtils.hasLength(nameSpaceUri) || CONTEXT_NAMESPACE_URI.equals(nameSpaceUri);
    }

    public void parsePropertyElement(Element beanElement, BeanDefinition bd) {
        final Iterator iterator = beanElement.elementIterator();
        while (iterator.hasNext()){
             Element pro = (Element) iterator.next();
            final String name = pro.attributeValue(NAME_ATTRIBUTE);
            if (!StringUtils.hasLength(name)){
                logger.error("this property is not fund 'name' ");
                return;
            }
            Object value = parsePropertyValue(pro,bd,name);
            final ProperTypeValue properTypeValue = new ProperTypeValue(name, value);
            bd.getProperValues().add(properTypeValue);
        }
    }

    public Object parsePropertyValue(Element pro, BeanDefinition bd, String properTypeName) {
        final boolean hasRef = pro.attribute(REF_ATTRIBUTE) != null;
        final boolean hasValue = pro.attribute(VALUE_ATTRIBUTE) != null;
        if (hasRef){
            final String ref = pro.attributeValue(REF_ATTRIBUTE);
            if (!StringUtils.hasText(ref)){
                logger.error(properTypeName + " ref is not null");
            }
            return new RuntimeBeanReference(ref);
        }else if (hasValue){
            final String value = pro.attributeValue(VALUE_ATTRIBUTE);
            return new TypeStringValue(value);
        }else {
            throw new RuntimeException(properTypeName + " must be have a value or ref");
        }
    }

    public void parseConstructorElements(Element element,BeanDefinition bd){
        final Iterator iterator = element.elementIterator(CONSTRUCTOR_ARG_ATTRIBUTE);
        while (iterator.hasNext()){
            final Element ele = (Element) iterator.next();
            parseConstructorArgElement(ele,bd);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        final Object value = parsePropertyValue(ele, bd, null);
        final ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        bd.getConstructorArgument().addArgumentValues(valueHolder);
    }
}
