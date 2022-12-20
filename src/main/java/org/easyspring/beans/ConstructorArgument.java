package org.easyspring.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xiangzhang
 * @since 2022-12-20 17:55
 */
public class ConstructorArgument {

    private final  List<ValueHolder> argumentValues = new ArrayList<>();

    public ConstructorArgument(){

    }

    public List<ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(this.argumentValues);
    }


    public void addArgumentValues(ValueHolder valueHolder) {
         this.argumentValues.add(valueHolder);
    }

    public Integer getArgumentValueCount(){
        return this.argumentValues.size();
    }

    public void clear(){
        this.argumentValues.clear();
    }

    // TODO 拓展
    public static class ValueHolder {
        private Object value;
        private String type;
        private String name;

        public ValueHolder(Object value){
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }



}
