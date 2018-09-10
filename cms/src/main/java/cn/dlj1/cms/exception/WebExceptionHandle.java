package cn.dlj1.cms.exception;

import cn.dlj1.cms.response.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
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

    @ExceptionHandler(MessageException.class)
    @ResponseBody
    public Result bindException(MessageException exception) {
        String message = exception.getMessage();
        Class clazz = exception.getClazz();
        log.error(String.format("[%s][%s]", clazz.getName(), message));
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


    /**
     * 通用消息异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindException(BindException exception) {
        return new Result.Fail(exception.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception exception) {
        exception.printStackTrace();
        log.error(String.format("异常:[%s]", exception.getMessage()));
        return Result.FAIL;
    }
}
