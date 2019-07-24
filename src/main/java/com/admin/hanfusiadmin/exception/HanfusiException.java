package com.admin.hanfusiadmin.exception;

import com.admin.hanfusiadmin.enums.HanfusiEnums;
import lombok.Getter;

/**
 * 描述：自定义异常类
 */

@Getter
public class HanfusiException extends RuntimeException {

    private Integer code;

    public HanfusiException(HanfusiEnums hanfusiEnums){
        super(hanfusiEnums.getMessage());

        this.code = hanfusiEnums.getCode();
    }

    public HanfusiException(Integer code, String message){
        super(message);

        this.code = code;
    }
}
