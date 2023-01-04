package org.easyspring.beans.factory.config;

import java.lang.reflect.Field;

public class DependencyDescriptor {
    private final Field field;
    private final boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        if (this.field != null){
            return field.getType();
        }
        throw new RuntimeException("only support field dependency.");
    }

    public boolean isRequired() {
        return required;
    }
}
