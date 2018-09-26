package cn.dlj1.cms.utils;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * 加密 解密工具类
 *
 * @author fivewords(443672581@qq.com)
 * @date 2016年11月15日
 */
public class Security {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    public static String md5(String string) {
        return encode("MD5", string);
    }

    /**
     * @param algorithm 加密类型
     *                  "SHA1", "SHA-256", "SHA-384", "SHA-512", "MD2", "MD5","base64"
     * @param string
     * @return
     */
    public static String encode(String algorithm, String string) {
        if (string == null) {
            return null;
        }
        try {
            if ("base64".equals(algorithm)) {
                return base64Encode(string.getBytes());
            }
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(string.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 解密
     *
     * @param algorithm 解密方式
     * @param string
     * @return
     */
    public static String deencode(String algorithm, String string) {
        if ("base64".equals(algorithm)) {
            return base64Decode(string);
        }
        return null;
    }

    /**
     * 将二进制字节数组转换成十六进制字符串，即加密后的16进制字符串
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            // Integer.toHexString(bytes[j]);
        }
        return buf.toString();
    }

    /**
     * 编码
     *
     * @param bstr
     * @return String
     */
    private static String base64Encode(byte[] bstr) {
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * 解码
     *
     * @param str
     * @return string
     */
    private static String base64Decode(String str) {
        try {
            byte[] bt = new sun.misc.BASE64Decoder().decodeBuffer(str);
            return new String(bt);
        } catch (IOException e) {
            return null;
        }

    }

}
