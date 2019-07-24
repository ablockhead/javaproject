package com.admin.hanfusiadmin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {

    private Integer goodsId;

    private String goodsName;

    private Integer goodsCurrency;

    private String goodsDetail;

    private String longImageUrl;

    private String acrossImageUrl;

    private String smallImageUrl;

    private Integer state;

    private Date createTime;

    private Date updateTime;
}
