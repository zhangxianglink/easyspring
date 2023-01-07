package org.easyspring.test.v5;


import org.easyspring.service.v5.PetStoreService5;
import org.easyspring.tx.TransactionManager;
import org.junit.Test;
import org.springframework.cglib.proxy.*;

import java.lang.reflect.Method;

public class CGLibTest  {

    @Test
    public void testCallBack(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService5.class);
        enhancer.setCallback(new TransactionInterceptor());
        PetStoreService5 o = (PetStoreService5) enhancer.create();
        o.placeOrder();
    }

    public static class TransactionInterceptor implements MethodInterceptor {
        TransactionManager tm = new  TransactionManager();
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tm.start();
            Object o1 = methodProxy.invokeSuper(o, objects);
            tm.commit();
            return o1;
        }
    }

    public static class proxyCallBackFilter implements CallbackFilter {

        @Override
        public int accept(Method method) {
            if (method.getName().startsWith("place")){
                return 0;
            }
            return 1;
        }
    }

    @Test
    public void testFilter(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(PetStoreService5.class);
        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};

        Class<?>[] types = new Class[callbacks.length];
        for (int i = 0; i < callbacks.length; i++) {
           types[i] = callbacks[i].getClass();
        }

        enhancer.setCallbackFilter(new proxyCallBackFilter());
        enhancer.setCallbackTypes(types);
        enhancer.setCallbacks(callbacks);
        PetStoreService5 o = (PetStoreService5) enhancer.create();
        o.placeOrder();
        System.out.println(o.toString());
    }
}
