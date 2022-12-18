package org.easyspring.test;

import org.easyspring.test.v2.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:07
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TypeConvertTest.class,CustomeNumberEditorTest.class,CustomBooleanEditorTest.class ,ApplicationContext.class, BeanDefinitionTest.class, BeanDefinitionValueResolverTest.class})
public class V2Tests {
}
