package org.easyspring.core.io;

import org.easyspring.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiangzhang
 * @since 2022-12-11 21:37
 */
public class FileSystemResource implements Resource{
    private final String path;
    private final File file;
    public FileSystemResource(String path) {
        Assert.notNull(path,"path is null");
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getDescription() {
        return "file from: " + this.file.getAbsolutePath() ;
    }
}
