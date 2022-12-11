package org.easyspring.beans;

/**
 * @author xiangzhang
 * @since 2022-12-11 15:29
 */
public class BeansException extends RuntimeException{
    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeansException(String message) {
        super(message);
    }
}
