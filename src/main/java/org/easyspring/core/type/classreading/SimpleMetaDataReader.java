package org.easyspring.core.type.classreading;

import org.easyspring.core.io.Resource;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;

public class SimpleMetaDataReader implements MetaDataReader{
    private final Resource resource;
    public  final ClassMetadata classMetadata;
    private final AnnotationMetaData annotationMetaData;

    public SimpleMetaDataReader(Resource resource) throws IOException {
        BufferedInputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;
        try {
            classReader = new ClassReader(is);
        }finally {
            is.close();
        }
        AnnotationMetaDataReadingVisitor visitor = new AnnotationMetaDataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);
        this.classMetadata = visitor;
        this.annotationMetaData = visitor;
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetaData getAnnotationMetaData() {
        return this.annotationMetaData;
    }
}
