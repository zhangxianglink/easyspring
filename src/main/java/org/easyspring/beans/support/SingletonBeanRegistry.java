package org.easyspring.beans.support;

/**
 * @author xiangzhang
 * @since 2022-12-14 15:58
 */
public interface SingletonBeanRegistry{

    void registerSingleton(String beanID, Object bean);
    Object getSingleton(String beanID);

}
