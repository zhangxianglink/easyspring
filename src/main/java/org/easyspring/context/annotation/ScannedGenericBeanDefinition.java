package org.easyspring.context.annotation;

import org.easyspring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.easyspring.beans.support.GenericBeanDefinition;
import org.easyspring.core.type.AnnotationMetaData;

public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private  final AnnotationMetaData metaData;
    public ScannedGenericBeanDefinition(AnnotationMetaData metaData) {
        super();
        this.metaData = metaData;
        setBeanClassName(this.metaData.getClassName());
    }
    public AnnotationMetaData getMetaData() {
        return metaData;
    }

}
