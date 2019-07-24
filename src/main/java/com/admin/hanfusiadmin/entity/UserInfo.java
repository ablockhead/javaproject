package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {

    private long userId;

    private String openId;

    private String nickName;

    private String avatarUrl;

    private String label;

    private String bgImg;

    private Integer currency;

    private Integer state;

    private Integer types;

    private double longitude;

    private double latitude;

    private int signUpFee;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
