package org.easyspring.core.type;

import org.easyspring.core.annotation.AnnotationAttributes;

import java.util.Set;

public interface AnnotationMetaData extends ClassMetadata{
     Set<String> getAnnotationTypes();

     AnnotationAttributes getAnnotationAttributes(String annotationType);

     boolean hasAnnotation(String annotationType);
}
