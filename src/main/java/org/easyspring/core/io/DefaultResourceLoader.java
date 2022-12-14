package org.easyspring.core.io;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.easyspring.beans.support.SingletonBeanRegistry;
import org.easyspring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangzhang
 * @since 2022-12-14 11:53
 */
public class DefaultResourceLoader implements ResourceLoader , SingletonBeanRegistry {

    @Nullable
    private ClassLoader classLoader;

    public void setClassLoader(@Nullable ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader();
    }


    // 省事儿，不独立实现
    private final Map<String,Object> singletonMap = new ConcurrentHashMap<>();

    @Override
    public void registerSingleton(@NotNull String beanID, Object bean) {
        Object singleton = getSingleton(beanID);
        if (singleton != null){
            throw new IllegalStateException("Could not register: " + bean + " under bean name: " + beanID + " bean is already: " + singleton);
        }
        this.singletonMap.put(beanID,bean);
    }

    @Override
    public Object getSingleton(String beanID) {
        return this.singletonMap.get(beanID);
    }
}
