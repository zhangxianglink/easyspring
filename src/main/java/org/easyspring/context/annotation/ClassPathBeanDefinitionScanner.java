package org.easyspring.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyspring.beans.BeanDefinition;
import org.easyspring.beans.factory.BeanDefinitionStoreException;
import org.easyspring.beans.factory.support.BeanNameGenerator;
import org.easyspring.beans.support.BeanDefinitionRegistry;
import org.easyspring.core.io.Resource;
import org.easyspring.core.io.support.PackageResourceLoader;
import org.easyspring.core.type.classreading.SimpleMetaDataReader;
import org.easyspring.stereotype.Component;
import org.easyspring.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ClassPathBeanDefinitionScanner {

    protected final Log log = LogFactory.getLog(getClass());
    private final BeanDefinitionRegistry registry;
    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan){
        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan, ",");

        LinkedHashSet<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage: basePackages){
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for(BeanDefinition candidate : candidates){
                beanDefinitions.add(candidate);
                registry.registryBeanDefinition(candidate.getBeanId(),candidate);
            }
        }
        return beanDefinitions;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        try {
            Resource[] resources = this.resourceLoader.getResources(basePackage);
            for (Resource resource : resources) {
                try {
                    SimpleMetaDataReader reader = new SimpleMetaDataReader(resource);
                    if (reader.getAnnotationMetaData().hasAnnotation(Component.class.getName())){
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(reader.getAnnotationMetaData());
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setBeanId(beanName);
                        candidates.add(sbd);
                    }
                }catch(Throwable ex){
                    throw new BeanDefinitionStoreException("failed to read candidate component class：" + resource, ex);
                }
            }
        }catch (Exception e){
           throw new RuntimeException("I/O scanner error");
        }
        return candidates;
    }


}
