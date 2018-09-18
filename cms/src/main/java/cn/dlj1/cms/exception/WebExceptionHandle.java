package cn.dlj1.cms.exception;

import cn.dlj1.cms.response.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class WebExceptionHandle {

    private static final Log log = LogFactory.getLog(WebExceptionHandle.class);

    // 自定义的消息异常
    @ExceptionHandler(MessageException.class)
    @ResponseBody
    public Result bindException(MessageException exception) {
        String message = exception.getMessage();
        Class clazz = exception.getClazz();
        String clazzName = null == clazz ? "" : clazz.getName();

        log.error(String.format("[%s][%s]", clazzName, message));
        return new Result.Fail(message);
    }

    // 方法参数类型不匹配
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Result methodArgumentTypeException(MethodArgumentTypeMismatchException exception) {
        log.error(String.format("[%s.%s][%s]参数类型不正确",
                exception.getParameter().getDeclaringClass(),
                exception.getParameter().getMethod().getName(),
                exception.getName()
        ));
        return new Result.Fail(String.format("参数[%s]类型不正确!", exception.getName()));
    }


    // 数据绑定异常
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindException(BindException exception) {
        String msg = exception.getFieldError().getDefaultMessage();
        log.error(String.format("数据绑定异常:[%s]", msg));
        if (msg.length() > 20) {
            // 排除英文错误
            msg = "数据格式不正确!";
        }
        msg = String.format("字段[%s]:%s!", exception.getFieldError().getField(), msg);
        return new Result.Fail(msg);
    }

    // 数据绑定异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String msg = exception.getMessage();
        return new Result.Fail(msg);
    }

    // 不支持的请求方式
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result bindException(HttpRequestMethodNotSupportedException exception) {
        return new Result.Fail("不支持的请求方式!");
    }

    // 如果没有找到类型
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception exception) {
        exception.printStackTrace();
        log.error(String.format("异常:[%s]", exception.getMessage()));
        return Result.FAIL;
    }
}
