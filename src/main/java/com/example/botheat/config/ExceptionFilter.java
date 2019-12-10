package com.example.botheat.config;

import com.example.botheat.bean.MessageFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author haya
 */
@Log4j2
@ControllerAdvice
@ResponseBody
public class ExceptionFilter {

    /**
     * 拦截所有优先级比自己低的异常
     * 同一个类下 拦截的异常越准确 优先级越高
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object defaultErrorHandler(Exception e) {
        log.info( e );
        return MessageFactory.instance( false );
    }
}
