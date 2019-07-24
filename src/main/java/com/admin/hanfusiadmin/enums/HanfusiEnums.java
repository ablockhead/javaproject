package com.admin.hanfusiadmin.enums;

import lombok.Getter;

/**
 * 描述：枚举类
 */

@Getter
public enum HanfusiEnums {

    NOT_MORE_ERROR(1001, "没有更多了"),
    UPDATE_ERROR(1002, "修改错误"),
    FILE_ERROR(1003, "文件不能为空"),
    UPLOAD_ERROR(1004, "上传文件出错"),
    ADD_ERROR(1005, "添加错误"),
    DEL_ERROR(1006, "删除错误"),
    USERNAME_ERROR(1007, "用户名不存在"),
    PASSWORD_ERROR(1008, "密码错误"),
    UNKNOWN_ERROR(4001, "系统错误");

    private Integer code;

    private String message;

    HanfusiEnums(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
