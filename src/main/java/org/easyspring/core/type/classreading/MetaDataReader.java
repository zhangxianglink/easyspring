package org.easyspring.core.type.classreading;

import org.easyspring.core.io.Resource;
import org.easyspring.core.type.AnnotationMetaData;
import org.easyspring.core.type.ClassMetadata;

public interface MetaDataReader {

    Resource getResource();

    ClassMetadata getClassMetadata();

    AnnotationMetaData getAnnotationMetaData();
}
