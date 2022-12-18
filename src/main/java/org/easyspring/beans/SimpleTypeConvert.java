package org.easyspring.beans;

import org.easyspring.beans.propertypeditors.CustomBooleanEditor;
import org.easyspring.beans.propertypeditors.CustomNumberEditor;
import org.easyspring.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangzhang
 * @since 2022-12-18 22:31
 */
public class SimpleTypeConvert implements TypeConvert{

    private  Map<Class<?>, PropertyEditor> defaultEditors;


    public SimpleTypeConvert(){

        this.defaultEditors = new HashMap<>(16);
        this.defaultEditors.put(boolean.class,new CustomBooleanEditor(false));
        this.defaultEditors.put(Boolean.class,new CustomBooleanEditor(true));

        this.defaultEditors.put(int.class,new CustomNumberEditor(Integer.class,false));
        this.defaultEditors.put(Integer.class,new CustomNumberEditor(Integer.class,true));
    }

    @Override
    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMisMatchException {
        if (ClassUtils.isAssignableValue(requiredType,value)){
            return (T) value;
        }else {
            if (value instanceof String){
                PropertyEditor propertyEditor = findDefaultPropertyEditor(requiredType);
                try {
                    propertyEditor.setAsText((String) value);
                }catch (IllegalArgumentException e){
                    throw new TypeMisMatchException();
                }
                return (T)propertyEditor.getValue();

            }else {
                throw new RuntimeException("this type can't support: "+ requiredType +" value :" + value);
            }
        }
    }

    private <T> PropertyEditor findDefaultPropertyEditor(Class<T> requiredType) {
        PropertyEditor propertyEditor = this.defaultEditors.get(requiredType);
        if (propertyEditor == null){
            throw new RuntimeException("Editor for " + requiredType + " has not been implemented");
        }
        return propertyEditor;
    }
}
