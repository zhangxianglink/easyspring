package org.easyspring.test;



import org.easyspring.test.v4.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:07
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({applicationContextTest4.class,AutowiredAnnotationProcessorTest.class,ClassPathBeanDefinitionScannerTest.class,ClassReaderTest.class,DependencyDescriptorTest.class,
        InjectionMetaDataTest.class,MetaDataReaderTest.class,PackageResourceLoadTest.class,XmlBeanDefinitionReaderTest.class})
public class V4Tests {
}
