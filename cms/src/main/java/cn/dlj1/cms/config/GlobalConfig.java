package cn.dlj1.cms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置
 */
@Component
@ConfigurationProperties(prefix = "ec")
public class GlobalConfig {

    /**
     * Application Name
     */
    private String name = "EC 管理系统";

    /**
     * default upload file size
     * default 5M
     */
    private long uploadFileSize = 1000 * 1000 * 5;

    /**
     * 上传文件默认格式
     */
    private String[] uploadFileExt = {"jpg", "png", "gif", "txt", "html"};

    /**
     * 模块可配置文件上传大小
     */
    private Map<String, Long> fileSize = new HashMap<>();

    /**
     * 模块可配置文件上传格式
     */
    private Map<String, String[]> fileExt = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUploadFileSize() {
        return uploadFileSize;
    }

    public void setUploadFileSize(long uploadFileSize) {
        this.uploadFileSize = uploadFileSize;
    }

    public String[] getUploadFileExt() {
        return uploadFileExt;
    }

    public void setUploadFileExt(String[] uploadFileExt) {
        this.uploadFileExt = uploadFileExt;
    }

    public Map<String, Long> getFileSize() {
        return fileSize;
    }

    public void setFileSize(Map<String, Long> fileSize) {
        this.fileSize = fileSize;
    }

    public Map<String, String[]> getFileExt() {
        return fileExt;
    }

    public void setFileExt(Map<String, String[]> fileExt) {
        this.fileExt = fileExt;
    }
}
