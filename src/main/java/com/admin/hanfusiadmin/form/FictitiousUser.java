package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FictitiousUser {

    private long userId;

    private String openId;

    private String nickName;

    private MultipartFile file;

    private String label;

    private Integer types = 1;
}
