package org.easyspring.beans.factory;

import org.easyspring.beans.BeansException;

/**
 * @author xiangzhang
 * @since 2022-12-11 15:31
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanDefinitionStoreException(String message) {
        super(message);
    }
}
