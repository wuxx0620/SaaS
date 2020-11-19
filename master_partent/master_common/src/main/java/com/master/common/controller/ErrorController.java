package com.master.common.controller;

import com.master.common.entity.Result;
import com.master.common.entity.ResultCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/19 17:10
 * @className ErrorController
 * @description TODO
 */

@RestController
@CrossOrigin
public class ErrorController {


    //公共错误跳转
    @RequestMapping(value = "autherror")
    public Result autherror(int code) {
        return code == 1 ? new Result(ResultCode.UNAUTHENTICATED) : new Result(ResultCode.UNAUTHORISE);
    }
}
