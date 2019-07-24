package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SocietiesForm {

    //话题名字
    private String topicName;


    //话题背景图
    private MultipartFile topicImg;


    // 话题类型 2-形制 3-社团
    private int types;

    // 动态内容
    private String content;

    // 话题Id;
    private int topicId;

    // 动态位置
    private String location;

    //userinfo
    private String openId;

    private String nickName;

    private MultipartFile avatarUrl;

    private int userId;

    private String adminName;

}
