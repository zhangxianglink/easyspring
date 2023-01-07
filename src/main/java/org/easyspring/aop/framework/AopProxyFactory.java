package org.easyspring.aop.framework;

public interface AopProxyFactory {
    Object getProxy();
    Object getProxy(ClassLoader classLoader);
}
