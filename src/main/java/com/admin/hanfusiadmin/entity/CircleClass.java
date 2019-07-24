package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class CircleClass {

    private int classId;

    private String circle_name;

    private String image_url;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;

    // 创建人uid
    private int uid;

    // 圈子介绍
    private String circle_presentation;

    // 圈主介绍
    private String master_presentation;

    // 0是隐私 1公开
    private int limits;

    //0未删除1已删除
    private int deleteid;

    // 0是没有V1是加V
    private int addV;
}
