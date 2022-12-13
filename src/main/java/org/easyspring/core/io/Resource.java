package org.easyspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:36
 */
public interface Resource {
    public InputStream getInputStream() throws IOException;
    public String getDescription();
}
