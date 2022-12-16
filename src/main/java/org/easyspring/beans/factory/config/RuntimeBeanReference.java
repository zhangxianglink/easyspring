package org.easyspring.beans.factory.config;

/**
 * @author xiangzhang
 * @since 2022-12-16 14:39
 */
public class RuntimeBeanReference {

    private final String refId;

    public RuntimeBeanReference(String refId) {
        this.refId = refId;
    }

    public String getRefId() {
        return refId;
    }
}
