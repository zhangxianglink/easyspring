package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;

public class AutowiredFieldElement extends InjectionElement{
    boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField(){
        return (Field) this.member;
    }
    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(descriptor);
            if (value != null){
                ReflectionUtils.makeAccessible(field);
                field.set(target,value);
            }
        }catch (Throwable ex){
            throw new BeanCreationException("could not autowired field " + field, ex);
        }
    }
}
