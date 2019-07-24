package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TopicTable {

    private long topicId;

    private String topicName;

    private long topicNum;

    private String topicImg;

    private long dynamicId;

    private Integer types;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Date updateTime;
}
