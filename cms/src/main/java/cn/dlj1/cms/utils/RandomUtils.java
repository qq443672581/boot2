package cn.dlj1.cms.utils;

import java.util.UUID;

/**
 * 随机数工具类
 *
 * @author fivewords(443672581@qq.com)
 * @date 2017年6月9日
 */
public class RandomUtils {

    public static final char[] a = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    public static final char[] A = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 获取一个UUID
     */
    public static String getUUID_() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取去掉 - 的UUID
     */
    public static String getUUID() {
        return getUUID_().replace("-", "");
    }

    /**
     * 获取一个六位的随机数字<br>
     * 一般用于短信验证码等
     */
    public static long getNumberVerifyCode() {
        return getNumber(6);
    }

    /**
     * 获取一个length长度的随机数字<br>
     * 最长 16位
     *
     * @param length
     * @return
     */
    public static long getNumber(int length) {
        if (length <= 1) {
            return (long) ((Math.random() * 9 + 1));
        }
        if (length > 16) {
            length = 16;
        }
        return (long) ((Math.random() * 9 + 1) * Math.pow(10, --length));
    }

    public static String getFileName(String ext) {
        return getUUID() + "." + ext;
    }

}
