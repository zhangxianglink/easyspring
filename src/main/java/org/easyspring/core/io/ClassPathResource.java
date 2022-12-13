package org.easyspring.core.io;

import org.easyspring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:37
 */
public class ClassPathResource implements Resource{

    private String path;
    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path,null);
    }

    public ClassPathResource(String path,ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    @Override
    public InputStream getInputStream() throws IOException {
         InputStream is = classLoader.getResourceAsStream(path);
         if (is == null){
           throw new FileNotFoundException(path + "cannot be opened ");
         }
        return is;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
