package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ActivityForm {

    private Integer activityId;

    private String activityName;

    private String activityImg;

    private MultipartFile activityFile;

    private String activityNotice;

    private Integer signUpFee;

    private String activityLocation;

    private double longitude;

    private double latitude;

    private Integer types;

    private String startTime;

    private String endTime;

    private String activityTime;

    private String adminName;
}
