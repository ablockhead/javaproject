package com.admin.hanfusiadmin.VO;

import lombok.Data;

/**
 * 描述：值对象:VO(Value object)
 * 1)封装控制层相关方法返回的数据
 * 2)统一服务端相关方法返回的数据格式
 */

@Data
public class ResultVO {

    // 状态码
    private Integer code;

    // 状态信息
    private String message;

    // 数据
    private Object data;
}
