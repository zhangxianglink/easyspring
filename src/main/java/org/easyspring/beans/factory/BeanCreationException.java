package org.easyspring.beans.factory;

import org.easyspring.beans.BeansException;

/**
 * @author xiangzhang
 * @since 2022-12-11 15:31
 */
public class BeanCreationException extends BeansException {

    private String beanName;

    public String getBeanName() {
        return beanName;
    }


    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String beanName, String message) {
        super("Error creating bean with name " + beanName + " message: "+ message);
        this.beanName = beanName;
    }
}
