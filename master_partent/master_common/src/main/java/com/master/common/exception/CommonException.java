package com.master.common.exception;

import com.master.common.entity.ResultCode;
import lombok.Getter;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 14:31
 * @className CommonException
 * @description TODO
 */
@Getter
public class CommonException extends Exception {

    private ResultCode resultCode;

    public CommonException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
