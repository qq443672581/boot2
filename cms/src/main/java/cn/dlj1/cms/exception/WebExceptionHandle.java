package cn.dlj1.cms.exception;

import cn.dlj1.cms.response.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sun.misc.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class WebExceptionHandle {

    private static final Log log = LogFactory.getLog(WebExceptionHandle.class);

    // 自定义的消息异常
    @ExceptionHandler(MessageException.class)
    @ResponseBody
    public Result messageException(MessageException exception) {
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

    // 数据库 unique 重复
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public Result bindException(DuplicateKeyException exception) {
        String msg = exception.getCause().getMessage();
        try {
            Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");
            Matcher matcher = pattern.matcher(msg);
            String field = null;
            String str = null;
            while (matcher.find()) {
                matcher.group(0);
                str = matcher.group(1);
                field = matcher.group(2);
                break;
            }
            msg = String.format("字段[%s]值[%s]重复", field, str);
        } catch (Exception e) {
            msg = "数据重复!";
        }

        return new Result.Fail(msg);
    }

    // json数据绑定格式异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result bindException(HttpMessageNotReadableException exception) {
        return messageException((MessageException) exception.getCause().getCause());
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
