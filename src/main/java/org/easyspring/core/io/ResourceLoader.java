package org.easyspring.core.io;

import com.sun.istack.internal.Nullable;

public interface ResourceLoader {

    @Nullable
    ClassLoader getClassLoader();
}
