package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.CircleClass;

public interface CircleClassService {

   ResultVO getAllCircleClassByIndex(int pageIndex,String adminName);

   ResultVO getAllCircleClassIndo();

   ResultVO getCircle(int classId);

   ResultVO deleteCircle(int classId);

   ResultVO updateCircleInfo(CircleClass circleClass);

   ResultVO getALLActivityByClassId(int classId,int pageIndex);
}
