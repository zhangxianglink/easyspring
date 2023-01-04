package org.easyspring.beans.factory.config;

import org.easyspring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);
}
