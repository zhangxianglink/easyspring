package org.easyspring.aop;

import java.lang.reflect.Method;

public interface MethodMatcher {
    public boolean matches(Method method) ;
}
