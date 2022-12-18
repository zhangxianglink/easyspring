package org.easyspring.test.v2;

import org.easyspring.beans.propertypeditors.CustomNumberEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-18 20:29
 */
public class CustomeNumberEditorTest  {

    @Test
    public void testConvertString(){
        final CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);
        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) value).intValue());

        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);

        try {
            editor.setAsText("3.1");
        }catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }
}
