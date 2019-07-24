package com.admin.hanfusiadmin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Classify {

    private Integer classifyId;

    private String title;

    private String url;

    private int sort;

    private String pages;

    private Date createTime;

    private Date updateTime;

}
