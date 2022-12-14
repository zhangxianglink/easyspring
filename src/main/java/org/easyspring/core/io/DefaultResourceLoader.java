package org.easyspring.core.io;

import com.sun.istack.internal.Nullable;
import org.easyspring.util.ClassUtils;

/**
 * @author xiangzhang
 * @since 2022-12-14 11:53
 */
public class DefaultResourceLoader implements ResourceLoader{

    @Nullable
    private ClassLoader classLoader;

    public void setClassLoader(@Nullable ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader();
    }
}
