package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class SheTuanXingzhi {
    private int topicId;
    private String topicName;
    private int dynamicId;
    private int types;
    private String content;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    private String openId;
}
