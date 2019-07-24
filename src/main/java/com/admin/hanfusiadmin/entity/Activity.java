package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Activity {

    private Integer activityId;

    private String activityName;

    private String activityImg;

    private String activityNotice;

    private Integer signUpFee;

    private String activityLocation;

    private double longitude;

    private double latitude;

    private Integer types;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityTime;

    // 提现状态 0 未发起提现 1提现中 2提现完成
    private int status;

    private int pepoleNum;
}
