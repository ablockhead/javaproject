package com.admin.hanfusiadmin.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 返回给前端的用户动态信息
 */

@Data
public class UserDynamicVO {

    // 动态Id
    private long dynamicId;

    // 动态话题Id
    private String topicId;

    // 动态内容
    private String content;

    // 动态图片
    private List<ImagesVO> imageUrl;

    // 经度
    private double longitude;

    // 纬度
    private double latitude;

    // 位置
    private String location;

    private int sort;

    // 创建时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 点赞数量
    private Integer likeNum;

    // 评论数量
    private Integer commentNum;
}
