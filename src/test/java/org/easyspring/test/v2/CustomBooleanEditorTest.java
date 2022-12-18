package org.easyspring.test.v2;

import org.easyspring.beans.propertypeditors.CustomBooleanEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-18 21:47
 */
public class CustomBooleanEditorTest {

    @Test
    public void testStringToBoolean(){
        CustomBooleanEditor editor = new CustomBooleanEditor(true);
        editor.setAsText("true");
        Assert.assertEquals(true,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("false");
        Assert.assertEquals(false,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("on");
        Assert.assertEquals(true,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("off");
        Assert.assertEquals(false,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("yes");
        Assert.assertEquals(true,((Boolean)editor.getValue()).booleanValue());

        editor.setAsText("no");
        Assert.assertEquals(
                false,((Boolean)editor.getValue()).booleanValue());


        try {
            editor.setAsText("aabbcc");
        }catch (IllegalArgumentException e){
            return;
        }
        Assert.fail();
    }
}
