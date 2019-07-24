package com.admin.hanfusiadmin.exceptionHandler;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 描述：捕获自定义异常
 */

@RestControllerAdvice
public class HanfusiExceptionHandler {

    @ExceptionHandler(value = HanfusiException.class)
    public ResultVO handlerDemoException(HanfusiException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
