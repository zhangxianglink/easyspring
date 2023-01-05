package org.easyspring.beans.factory.annotation;

import org.easyspring.beans.BeansException;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.easyspring.beans.support.AbstractBeanFactory;
import org.easyspring.beans.support.DefaultBeanFactory;
import org.easyspring.core.annotation.AnnotationUtils;
import org.easyspring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {
    private AbstractBeanFactory factory;
    private String requiredParameterName = "required";
    private boolean requiredParamterValue = true;
    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>();

    public AutowiredAnnotationProcessor() {
        autowiredAnnotationTypes.add(Autowired.class);
    }

    public void setBeanFactory(AbstractBeanFactory beanFactory) {
        this.factory = beanFactory;
    }

    public InjectionMetaData buildAutowiredMetadata(Class<?> clazz) {
        LinkedList<InjectionElement> elements = new LinkedList<>();
        Class<?> targetClass = clazz;
        do {
            LinkedList<InjectionElement> curElements = new LinkedList<>();
            for (Field field : targetClass.getDeclaredFields()){
                Annotation ann = findAutowiredAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())){
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    curElements.add(new AutowiredFieldElement(field, required, factory));
                }
            }
            // TODO method 处理
            elements.addAll(0, curElements);
            targetClass = targetClass.getSuperclass();
        }while (targetClass != null && targetClass != Object.class);
        return new InjectionMetaData(clazz,elements);
    }

    private boolean determineRequiredStatus(Annotation ann) {
        Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
        if (method == null){
            return true;
        }
        return this.requiredParamterValue = (Boolean) ReflectionUtils.invokeMethod(method, ann);
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes){
            Annotation annotation = AnnotationUtils.getAnnotation(ao, type);
            if (annotation != null){
                return annotation;
            }
        }
        return null;
    }

    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetaData metaData = buildAutowiredMetadata(bean.getClass());
        try {
            metaData.inject(bean);
        }catch (Throwable ex){
            throw new BeanCreationException(beanName);
        }

    }
}
