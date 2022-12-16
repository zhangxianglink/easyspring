package org.easyspring.beans;

/**
 * @author xiangzhang
 * @since 2022-12-16 14:19
 */
public class ProperTypeValue {
    private final String name;
    private final Object value;
    private Object convertedValue;

    private boolean converted = false;

    public ProperTypeValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public synchronized boolean isConverted(){
        return this.isConverted();
    }

    public synchronized void setConvertedValue(Object convertedValue){
        this.converted = true;
        this.convertedValue =  convertedValue;
    }

    public String getName() {
        return this.name;
    }


    public Object getValue() {
        return this.value;
    }


    public Object getConvertedValue(){
        return this.convertedValue;
    }
}
