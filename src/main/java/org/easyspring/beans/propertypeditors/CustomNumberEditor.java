package org.easyspring.beans.propertypeditors;

import org.easyspring.util.NumberUtils;
import org.easyspring.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;

/**
 * @author xiangzhang
 * @since 2022-12-18 20:40
 */
public class CustomNumberEditor extends PropertyEditorSupport {
    private final Class<? extends Number> clz;
    private final NumberFormat numberFormat;
    private final boolean allowEmpty;

    public  CustomNumberEditor(Class<? extends Number> clz, boolean allowEmpty) throws IllegalArgumentException{
        this(clz,null,allowEmpty);
    }

    public  CustomNumberEditor(Class<? extends Number> clz, NumberFormat numberFormat, boolean allowEmpty)  throws IllegalArgumentException{
        if (!Number.class.isAssignableFrom(clz) ){
            throw new IllegalArgumentException("Property class must be a subclass of Number");
        }
        this.allowEmpty= allowEmpty;
        this.clz = clz;
        this.numberFormat = numberFormat;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)){
            setValue(null);
        }else if (this.numberFormat != null){
            setValue(NumberUtils.parseNumber(text,this.clz,this.numberFormat));
        }else {
            setValue(NumberUtils.parseNumber(text,this.clz));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number){
            super.setValue(NumberUtils.convertNumberToTargetClass((Number) value,this.clz));
        }else {
            super.setValue(value);
        }
    }


}
