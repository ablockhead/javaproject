package com.admin.hanfusiadmin.form;

import lombok.Data;

@Data
public class FictitiousDynamic {

    private String openId;

    private String content;

    private Integer photoType;

    private double longitude = -1;

    private double latitude = -1;

    private String location;
}
