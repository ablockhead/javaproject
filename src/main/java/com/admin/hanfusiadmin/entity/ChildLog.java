package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ChildLog {

    private int id;

    private String childName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;

    private String handleDetail;

    // 添加的Id
    private long typesId=0;

    // 1-社团 2-活动 3-圈子
    private int types=0;
}
