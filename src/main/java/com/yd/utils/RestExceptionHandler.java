package com.yd.utils;

import com.alibaba.fastjson.JSONException;
import com.tps.common.Result;
import com.tps.common.utils.LogUtil;
import com.tps.exception.LogicException;
import com.tps.meta.commons.ResultUtil;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理类
 *
 * @date 2017-09-22 12:15
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
    private static final Logger LOGGER = LogUtil.getLogger();

    /**
     * 异常拦截处理
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    private Result runtimeExceptionHandler(HttpServletResponse response, Exception e) {
        if (e instanceof LogicException) {
            LogicException logicException = (LogicException) e;
            LOGGER.error(logicException.getMessage(), logicException);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return logicException.getResult();
        }
        if (e instanceof JSONException || e instanceof HttpMessageNotReadableException || e instanceof MethodArgumentTypeMismatchException) {
            LOGGER.error(e.getMessage(), e);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            LOGGER.error("result.mgs.parameter.illegality", e);
            return ResultUtil.generate("600");
        }
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        LOGGER.error("系统繁忙，请稍候再试...", e);
        return ResultUtil.generate("999");
    }

}
