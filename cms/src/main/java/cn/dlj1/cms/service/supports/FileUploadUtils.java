package cn.dlj1.cms.service.supports;

import cn.dlj1.cms.utils.DateUtils;
import cn.dlj1.cms.utils.RandomUtils;
import sun.misc.BASE64Encoder;

import java.io.File;

public class FileUploadUtils {

    /**
     * 获取一个文件名
     * 保证在一天内不重复
     *
     * @param ext
     * @return
     */
    public static String getFileName(String ext) {
        String base = new BASE64Encoder().encode(
                (RandomUtils.getNumber(3) +
                        DateUtils.getDateString(DateUtils.getNow(), "HHmmssSSS")).getBytes()
        );
        return base + "." + ext;
    }

    public static String getFileRelationPath(String rootPath, String ext) {
        String dirPath = DateUtils.getDateString(DateUtils.getNow(),
                File.separator + "yyyyMM" + File.separator + "dd");
        File file = new File(rootPath + dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dirPath + File.separator + getFileName(ext);

    }

}
