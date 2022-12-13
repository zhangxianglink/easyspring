package org.easyspring.util;

/**
 * @author xiangzhang
 * @since 2022-12-12 23:06
 */
public abstract class Assert {
    public static void notNull(Object obj, String msg){
        if (obj == null){
            throw new IllegalArgumentException(msg);
        }
    }
}
