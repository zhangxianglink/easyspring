package org.easyspring.beans.support;

import org.apache.commons.beanutils.BeanUtils;
import org.easyspring.beans.*;
import org.easyspring.beans.factory.BeanCreationException;
import org.easyspring.beans.factory.BeanFactory;
import org.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import org.easyspring.beans.factory.config.BeanPostProcessor;
import org.easyspring.beans.factory.config.DependencyDescriptor;
import org.easyspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.easyspring.beans.factory.context.support.BeanDefinitionValueResolver;
import org.easyspring.beans.factory.context.support.ConstructorResolver;
import org.easyspring.core.io.DefaultResourceLoader;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiangzhang
 * @since 2022-12-15 16:14
 */
public abstract class AbstractBeanFactory extends DefaultResourceLoader implements BeanFactory, BeanDefinitionRegistry , AutowireCapableBeanFactory {

    private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
    @Override
    public Object getBean(String beanID) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanID);
        if (bd == null){
            throw new BeanCreationException("bean definition does not exist");
        }
        return this.getBeanByDefinition(bd);
    }

    public Object createBean(BeanDefinition bd) {
        // 生成实例
        Object bean =  instantiateBean(bd);
        // 设置属性
        populateBean(bd,bean);
        return bean;
    }

    private Object instantiateBean(BeanDefinition bd) {
        if (bd.hasConstructorArgument()){
            final ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }
        ClassLoader cl = this.getClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " is  error");
        }
    }

    /**
     * BeanUtils.setProperty 替代populateBean 自己实现
     * @param bd
     * @param bean
     */
    private void populateBeanUseCommonBeanUtils(BeanDefinition bd , Object bean){
        final BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        final List<ProperTypeValue> pvs = bd.getProperValues();
        final ConstructorArgument constructorArgument = bd.getConstructorArgument();
        if (pvs == null || pvs.isEmpty()){
            return;
        }
        try {
            for(ProperTypeValue pt: pvs){
                final String name = pt.getName();
                final Object value = pt.getValue();
                final Object resolveValue = resolver.resolveValueIfNecessary(value);
                BeanUtils.setProperty(bean,name,resolveValue);
            }
        }catch (Exception e){
            throw new RuntimeException("CommonBeanUtils  " + bd.getBeanClassName());
        }
    }


    private void populateBean(BeanDefinition bd, Object bean){
        for (BeanPostProcessor processor : this.getBeanPostProcessor()){
            if (processor instanceof InstantiationAwareBeanPostProcessor){
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean,bd.getBeanId());
            }
        }

        final BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        final List<ProperTypeValue> pvs = bd.getProperValues();
        if (pvs == null || pvs.isEmpty()){
            return;
        }
        final SimpleTypeConvert convert = new SimpleTypeConvert();
        try {
            for(ProperTypeValue pt: pvs){
                final String name = pt.getName();
                final Object value = pt.getValue();
                final Object resolveValue = resolver.resolveValueIfNecessary(value);

                final BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : descriptors){
                    if (descriptor.getName().equals(name)){
                        final Object convertedValue = convert.convertIfNecessary(resolveValue, descriptor.getPropertyType());
                        descriptor.getWriteMethod().invoke(bean,convertedValue);
                        break;
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException("failed to obtain beanInfo for class " + bd.getBeanClassName());
        }
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> type = descriptor.getDependencyType();
        for (BeanDefinition bd: this.beanDefinitionMap.values()){
            resovleBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if (type.isAssignableFrom(beanClass)){
                return  this.getBean(bd.getBeanId());
            }
        }
        return null;
    }

    private void resovleBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()){
            return;
        }else {
            try {
                bd.resolveBeanClass();
            }catch (ClassNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void registryBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID,bd);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessor() {
        return this.beanPostProcessors;
    }

    protected abstract Object getBeanByDefinition(BeanDefinition bd );

    @Override
    public Class<?> getType(String targetBeanName) throws NoSuchBeanDefinitionException {
        BeanDefinition beanDefinition = this.getBeanDefinition(targetBeanName);
        if (beanDefinition == null){
            throw new NoSuchBeanDefinitionException(targetBeanName);
        }
        resovleBeanClass(beanDefinition);
        return beanDefinition.getBeanClass();
    }
}
