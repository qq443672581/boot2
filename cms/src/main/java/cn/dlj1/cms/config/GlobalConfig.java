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
        if (StringUtils.isEmpty(fileUploadRootPath)) {
            logger.warn("file upload root path not exist, to setting [ec.file-upload-root-path]");
        }
    }

    /**
     * Application Name
     */
    private String name = "EC 管理系统";

    /**
     * upload file default save path
     */
    private String fileUploadRootPath;

    /**
     * upload file size max size <br>
     * default 5M
     */
    private long fileUploadSize = 1000 * 1000 * 5;

    /**
     * default upload file format <br>
     * array
     */
    private String[] fileUploadExt = {"jpg", "png", "gif", "txt", "js", "html"};

    /**
     * module upload file size <br>
     * key:     module entity real packages <br>
     * value:   unit,byte <br>
     * eg : <br>
     * com.xxx.entity.Test : 100000
     */
    private Map<String, Long> fileUploadModuleSize = new HashMap<>();

    /**
     * module upload file format <br>
     * key:     module entity real packages <br>
     * value:   string array <br>
     */
    private Map<String, String[]> fileUploadModuleExt = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileUploadRootPath() {
        return fileUploadRootPath;
    }

    public void setFileUploadRootPath(String fileUploadRootPath) {
        this.fileUploadRootPath = fileUploadRootPath;
    }

    public long getFileUploadSize() {
        return fileUploadSize;
    }

    public void setFileUploadSize(long fileUploadSize) {
        this.fileUploadSize = fileUploadSize;
    }

    public String[] getFileUploadExt() {
        return fileUploadExt;
    }

    public void setFileUploadExt(String[] fileUploadExt) {
        this.fileUploadExt = fileUploadExt;
    }

    public Map<String, Long> getFileUploadModuleSize() {
        return fileUploadModuleSize;
    }

    public void setFileUploadModuleSize(Map<String, Long> fileUploadModuleSize) {
        this.fileUploadModuleSize = fileUploadModuleSize;
    }

    public Map<String, String[]> getFileUploadModuleExt() {
        return fileUploadModuleExt;
    }

    public void setFileUploadModuleExt(Map<String, String[]> fileUploadModuleExt) {
        this.fileUploadModuleExt = fileUploadModuleExt;
    }
}
