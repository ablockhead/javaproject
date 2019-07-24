package com.admin.hanfusiadmin.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class OrdersVO {

    private Integer ordersId;

    private String nickName;

    private String avatarUrl;

    private String goodsName;

    private Integer goodsCurrency;

    private String phone;

    private String address;

    private Integer state;

    private BigInteger courierNumber;

    private String couriterName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
