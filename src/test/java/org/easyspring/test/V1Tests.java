package org.easyspring.test;

import org.easyspring.test.v1.ApplicationContextTest;
import org.easyspring.test.v1.BeanFactoryTest;
import org.easyspring.test.v1.ResourceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:07
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTest.class, BeanFactoryTest.class, ResourceTest.class})
public class V1Tests {
}
