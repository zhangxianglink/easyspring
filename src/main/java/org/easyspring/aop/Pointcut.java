package org.easyspring.aop;

public interface Pointcut {

    String getExpression();
    MethodMatcher getMethodMatcher();
}
