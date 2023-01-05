package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

public abstract class InjectionElement {
    protected Member member;
    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    /**
     * 从factory根据类型获取实例，并且注入
     * @param target
     */
    public abstract void inject(Object target);
}
