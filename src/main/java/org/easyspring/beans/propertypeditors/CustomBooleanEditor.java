package org.easyspring.beans.propertypeditors;

import org.easyspring.util.StringUtils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

/**
 * @author xiangzhang
 * @since 2022-12-18 21:55
 */
public class CustomBooleanEditor extends PropertyEditorSupport {

    public static final String VALUE_TRUE   = "true"   ;
    public static final String VALUE_FALSE  = "false"  ;
    public static final String  VALUE_ON    = "on" ;
    public static final String  VALUE_OFF   = "off";
    public static final String  VALUE_YES   = "yes";
    public static final String  VALUE_NO    = "no" ;
    public static final String  VALUE_1   = "1";
    public static final String  VALUE_0    = "0" ;
    private final boolean allowEmpty;
    public CustomBooleanEditor(boolean bool) {
        this.allowEmpty = bool;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
         text = (text != null ? text.trim() : null);
        if (this.allowEmpty || !StringUtils.hasLength(text)){
            setValue(null);
        } else
        if (VALUE_FALSE.equalsIgnoreCase(text) || VALUE_NO.equalsIgnoreCase(text) || VALUE_OFF.equalsIgnoreCase(text) || VALUE_1.equalsIgnoreCase(text)){
            setValue(false);
        }
        else if (VALUE_ON.equalsIgnoreCase(text) || VALUE_TRUE.equalsIgnoreCase(text) || VALUE_YES.equalsIgnoreCase(text) || VALUE_0.equalsIgnoreCase(text) ){
            setValue(true);
        }else {
            throw new IllegalArgumentException("invalid boolean value: " + text);
        }
    }

    @Override
    public String getAsText() {
         if (Boolean.TRUE.equals(getValue())){
             return VALUE_TRUE;
         } else if (Boolean.FALSE.equals(getValue())){
            return VALUE_FALSE;
        }else {
            return "";
        }
    }
}
