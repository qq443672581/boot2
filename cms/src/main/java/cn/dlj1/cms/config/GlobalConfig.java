package cn.dlj1.cms.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Config <br>
 * <br>
 * <br>
 */
@Component
@ConfigurationProperties(prefix = "ec")
public class GlobalConfig implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(GlobalConfig.class);

    @Override
    public void run(String... args) throws Exception {
        if (StringUtils.isEmpty(fileUpload.getRootPath())
                || !new java.io.File(fileUpload.getRootPath()).exists()) {
            logger.warn("file upload root path not exist, to setting [ec.file-upload.root-path]");
        }
    }

    /**
     * Application Name
     */
    private String name = "EC Content Manger System";

    /**
     * file upload config
     */
    private File fileUpload = new File();

    public static class File {
        /**
         * upload file default save path
         */
        private String rootPath;

        /**
         * upload file size max size <br>
         * default 5M
         */
        private long size = 1000 * 1000 * 5;

        /**
         * default upload file format <br>
         * array
         */
        private String[] ext = {"jpg", "png", "gif", "txt", "js", "html"};

        /**
         * module upload file size <br>
         * key:     module entity real packages <br>
         * value:   unit,byte <br>
         * eg : <br>
         * com.xxx.entity.Test : 100000
         */
        private Map<String, Long> moduleSize = new HashMap<>();

        /**
         * module upload file format <br>
         * key:     module entity real packages <br>
         * value:   string array <br>
         */
        private Map<String, String[]> moduleExt = new HashMap<>();

        public String getRootPath() {
            return rootPath;
        }

        public void setRootPath(String rootPath) {
            this.rootPath = rootPath;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public String[] getExt() {
            return ext;
        }

        public void setExt(String[] ext) {
            this.ext = ext;
        }

        public Map<String, Long> getModuleSize() {
            return moduleSize;
        }

        public void setModuleSize(Map<String, Long> moduleSize) {
            this.moduleSize = moduleSize;
        }

        public Map<String, String[]> getModuleExt() {
            return moduleExt;
        }

        public void setModuleExt(Map<String, String[]> moduleExt) {
            this.moduleExt = moduleExt;
        }
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    public File getFileUpload() {
        return fileUpload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}