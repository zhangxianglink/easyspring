package org.easyspring.test;


import org.easyspring.test.v3.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:07
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTest.class,BeanDefinitionTest.class,ConstructorResolverTest.class})
public class V3Tests {
}
