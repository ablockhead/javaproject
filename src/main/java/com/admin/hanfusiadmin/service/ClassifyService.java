package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.OfficialTopicVO;
import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.form.ActivityForm;
import com.admin.hanfusiadmin.form.AddClassifyForm;
import com.admin.hanfusiadmin.form.EditFeilei;
import com.admin.hanfusiadmin.form.SocietiesForm;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface ClassifyService {

    ResultVO getClassifyList();

    ResultVO getClassifyById(int classifyId);

    ResultVO updateFenlei(EditFeilei editFeilei);

    ResultVO getAllClassifyList(int index);

    ResultVO addClassifyInfo(AddClassifyForm addClassifyForm);

    ResultVO deleteClassifyById(int classifyId,int sort);

    ResultVO getClassify(Integer classifyId);

    ResultVO updateClassify(MultipartHttpServletRequest request, OfficialTopicVO officialTopicVO);

    ResultVO getActivityList(Integer pageIndex,String adminName);

    ResultVO addActivity(ActivityForm activityForm);

    ResultVO delActivity(Integer activityId);

    ResultVO getUnderTheLine();

    ResultVO getUnderTheLineUser(Integer activityId, Integer pageIndex);

    ResultVO getSheTuanXingzhi(String childName);

    ResultVO addSheTuanXingzhi(SocietiesForm societiesForm);
}
