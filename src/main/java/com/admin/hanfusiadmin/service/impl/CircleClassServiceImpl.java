package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.ChildLogDao;
import com.admin.hanfusiadmin.dao.CircleClassDao;
import com.admin.hanfusiadmin.entity.Activity;
import com.admin.hanfusiadmin.entity.CircleActivity;
import com.admin.hanfusiadmin.entity.CircleClass;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.service.CircleClassService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CircleClassServiceImpl implements CircleClassService{

    @Autowired
    CircleClassDao circleClassDao;

    @Autowired
    ChildLogDao childLogDao;

    /**
     * 获取所有圈子信息
     * @return 信息集合
     */
    @Override
    public ResultVO getAllCircleClassIndo() {
        return ResultVOUtil.success(circleClassDao.getAllCircleClassList());
    }

    /**
     * 分页获取圈子下的所有活动
     * @param classId 圈子Id
     * @param pageIndex 当前页
     * @return 活动信息集合
     */
    @Override
    public ResultVO getALLActivityByClassId(int classId,int pageIndex) {
        int totalCount = circleClassDao.getActiviCount(classId);
            if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }
        pageIndex = (pageIndex - 1) * 6;
        List<CircleActivity> circleActivities = circleClassDao.getActiviByIndexAndClassId(pageIndex,classId);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("circleActivities", circleActivities);

        return ResultVOUtil.success(map);
    }

    /**
     * 分页所有获取圈子的信息
     * @param pageIndex 当前页
     * @return 分页后的圈子集合
     */
    @Override
    public ResultVO getAllCircleClassByIndex(int pageIndex,String adminName) {

        int totalCount = 0;
        if(adminName.equals("admin")){
            System.out.println(adminName);
            totalCount = circleClassDao.getCircleClasCount();
        }else{
            totalCount = childLogDao.getActivityCount(3,adminName);
        }
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }
        pageIndex = (pageIndex - 1) * 6;

        List<CircleClass> circleClasses =adminName.equals("admin") ?
                circleClassDao.getCircleClassList(pageIndex) : circleClassDao.getCircleChildClassList(adminName,pageIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("circleClasses", circleClasses);
        return ResultVOUtil.success(map);
    }

    /**
     * 获取圈子信息
     * @param classId 圈子Id
     * @return 圈子信息
     */
    @Override
    public ResultVO getCircle(int classId) {
        CircleClass circleClass = circleClassDao.getCircleInfo(classId);
        return circleClass == null ? ResultVOUtil.error(50000,"没有该圈子") : ResultVOUtil.success(circleClass);
    }

    /**
     * 删除圈子
     * @param classId 圈子Id
     * @return 结果
     */
    @Override
    public ResultVO deleteCircle(int classId) {
        return circleClassDao.deleteCircleClass(classId) > 0 ?
                ResultVOUtil.success() : ResultVOUtil.error(50000,"删除失败");
    }

    /**
     * 更新圈子信息
     * @param circleClass  圈子信息
     * @return 结果
     */
    @Override
    public ResultVO updateCircleInfo(CircleClass circleClass) {
        return circleClassDao.updateCircleInfo(circleClass) > 0 ?
                ResultVOUtil.success() : ResultVOUtil.error(50000,"更新失败，请稍后重试");
    }
}
