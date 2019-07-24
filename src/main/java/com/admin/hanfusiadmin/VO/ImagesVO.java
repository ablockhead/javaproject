package com.admin.hanfusiadmin.VO;

import lombok.Data;

/**
 * 返回给前端的照片实体类
 */

@Data
public class ImagesVO {

    // 图片地址
    private String url;

    // 封面图片
    private String coverUrl;

    // 类型，1照片，2视频
    private Integer photoType;
}
