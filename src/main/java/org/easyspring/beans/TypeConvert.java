package org.easyspring.beans;

public interface TypeConvert {
    <T> T convertIfNecessary(Object i, Class<T> requiredType) throws TypeMisMatchException;
}
