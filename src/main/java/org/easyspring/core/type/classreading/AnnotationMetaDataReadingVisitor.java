package org.easyspring.core.type.classreading;

import org.easyspring.core.annotation.AnnotationAttributes;
import org.easyspring.core.type.AnnotationMetaData;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class AnnotationMetaDataReadingVisitor extends CLassMetadataReadingVisitor implements AnnotationMetaData {
    private final Set<String> annotationSet = new LinkedHashSet<>();
    private final Map<String, AnnotationAttributes> attributesMap = new LinkedHashMap<>();

    public AnnotationMetaDataReadingVisitor(){}

    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String className = Type.getType(desc).getClassName();
        this.annotationSet.add(className);
         return new AnnotationAttributesReadingVisitor(className,this.attributesMap);
    }

    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    public Map<String, AnnotationAttributes> getAttributesMap() {
        return this.attributesMap;
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributesMap.get(annotationType);
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }
}
