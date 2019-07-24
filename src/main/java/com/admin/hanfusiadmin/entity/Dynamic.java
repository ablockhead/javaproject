package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Dynamic {

    private long dynamicId;

    private String openId;

    private String topicId;

    private String content;

    private double longitude;

    private double latitude;

    private String location;

    private String rqCode;

    private int sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
