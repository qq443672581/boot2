package cn.dlj1.cms.exception;

/**
 * 通用消息异常
 */
public class MessageException extends RuntimeException {

    private Class clazz;

    public MessageException() {

    }

    public MessageException(Class clazz, String message) {
        super(message);
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
