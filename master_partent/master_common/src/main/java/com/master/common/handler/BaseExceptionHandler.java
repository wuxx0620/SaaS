package com.master.common.handler;

import com.master.common.entity.Result;
import com.master.common.entity.ResultCode;
import com.master.common.exception.CommonException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PublicKey;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 14:34
 * @className BaseExceptionHandler
 * @description TODO
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        if (e.getClass() == CommonException.class) {
            CommonException ce = (CommonException) e;
            return new Result(ce.getResultCode());
        } else {
            return new Result(ResultCode.SERVER_ERROR);
        }
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result error() {
        return new Result(ResultCode.UNAUTHORISE);
    }
}
