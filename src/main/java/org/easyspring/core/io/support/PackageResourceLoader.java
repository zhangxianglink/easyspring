package org.easyspring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easyspring.core.io.FileSystemResource;
import org.easyspring.core.io.Resource;
import org.easyspring.util.Assert;
import org.easyspring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiangzhang
 * @since 2022-12-27 17:02
 */
public class PackageResourceLoader {

    private static final Log log = LogFactory.getLog(PackageResourceLoader.class);


    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader," classLoader is null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader(){
        return this.classLoader;
    }


    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage," basePackage is null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(location);
        File rootDir = new File(url.getFile());

        Set<Path> matchingFiles = retrieveMatchingFiles(rootDir);
        final Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for (Path path: matchingFiles){

            result[i++] = new FileSystemResource( path.toFile());
        }
        return result;
    }

    private Set<Path> retrieveMatchingFiles(File rootDir) throws IOException {
        if (!rootDir.exists()){
            if (log.isDebugEnabled()){

            }
        }
        if (!rootDir.isDirectory()){
            if (log.isDebugEnabled()){

            }
        }
        if (!rootDir.canRead()){
            if (log.isDebugEnabled()){

            }
        }
        try(Stream<Path> walk = Files.walk(Paths.get(rootDir.getAbsolutePath()))) {
            final Set<Path> paths = walk.filter(Files::isRegularFile).collect(Collectors.toSet());
            return paths;
        }
    }
}
