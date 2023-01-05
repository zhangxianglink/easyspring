package org.easyspring.beans.factory.annotation;

import java.util.LinkedList;
import java.util.Objects;

public class InjectionMetaData {

    private final Class<?> targetClass;
    private final  LinkedList<InjectionElement> injectionElements;
    public InjectionMetaData(Class<?> targetClass, LinkedList<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }

    public LinkedList<InjectionElement> getInjectionElements() {
        return injectionElements;
    }


    public void inject(Object target) {
        if (injectionElements  == null || injectionElements.isEmpty()){
            return;
        }
        for(InjectionElement injectionElement : injectionElements)
        {
            injectionElement.inject(target);
        }
    }
}
