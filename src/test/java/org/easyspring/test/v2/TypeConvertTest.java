package org.easyspring.test.v2;

import org.easyspring.beans.SimpleTypeConvert;
import org.easyspring.beans.TypeConvert;
import org.easyspring.beans.TypeMisMatchException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author xiangzhang
 * @since 2022-12-18 22:16
 */
public class TypeConvertTest {

    @Test
    public void testConvert() throws TypeMisMatchException {
        TypeConvert convert = new SimpleTypeConvert();
       Integer i = convert.convertIfNecessary(3,Integer.class);
        Assert.assertEquals(3,i.intValue());
        try {
            convert.convertIfNecessary("aba",Integer.class);
        }catch (TypeMisMatchException e){
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConvert2() throws TypeMisMatchException {
        TypeConvert convert = new SimpleTypeConvert();
        Boolean i = convert.convertIfNecessary("yes",Boolean.class);
        Assert.assertEquals(true ,i.booleanValue());
        try {
            convert.convertIfNecessary("3.1",Boolean.class);
        }catch (TypeMisMatchException e){
            return;
        }
        Assert.fail();
    }
}
