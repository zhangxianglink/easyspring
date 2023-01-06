package org.easyspring.test.v5;

import org.easyspring.aop.MethodMatcher;
import org.easyspring.aop.aspectj.AspectJExpressionPointcut;
import org.easyspring.service.v4.PetStoreService;
import org.easyspring.service.v5.PetStoreService5;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class PointCutTest {

    @Test
    public void testPointCut() throws Exception {
        String expression = "execution(* org.easyspring.service.v5.*.placeOrder(..))";
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        MethodMatcher mm = pointcut.getMethodMatcher();
        {
            Class<PetStoreService5> targetClass = PetStoreService5.class;
            Method method = targetClass.getMethod("placeOrder");
            Assert.assertTrue(mm.matches(method));

            Method method1 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method1));
        }

        {
            Class<PetStoreService> targetClass = PetStoreService.class;
            Method method1 = targetClass.getMethod("getAccountDao");
            Assert.assertFalse(mm.matches(method1));
        }
     }
}
