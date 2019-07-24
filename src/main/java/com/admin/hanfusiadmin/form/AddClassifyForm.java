package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddClassifyForm {
    // 分类背景图
    private MultipartFile file;

    // 排序
    private  int sort;

    // 标题
    private String title;

    // 跳转页面地址
    private String pages;

}
