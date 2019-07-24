package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditUserInfo {
    // 头像文件
    private MultipartFile file;

    // 昵称
    private String nickName;

    //标签
    private String label;

    // 用户当前状态
    private  int state;

    //汉服币数量
    private  int currency;

    //经度
    private  double longitude;

    //纬度
    private  double latitude;

    // 用户openId
    private  String openId;
}
