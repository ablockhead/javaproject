package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.CircleClass;
import com.admin.hanfusiadmin.service.CircleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("circle")
public class CircleClassController {
    @Autowired
    CircleClassService circleClassService;

    /**
     * 分页所有获取圈子的信息
     * @param pageIndex 当前页
     * @return 分页后的圈子集合
     */
    @RequestMapping("getAllCircleByInde")
    ResultVO getAllCircleByIndex(@RequestParam("pageIndex") int pageIndex,@RequestParam("adminName") String adminName){
        return circleClassService.getAllCircleClassByIndex(pageIndex,adminName);
    }

    /**
     * 分页查询圈子下所有活动
     * @param classId 圈子Id
     * @param pageIndex 当前页
     * @return 活动信息集合
     */
    @RequestMapping("getALLActivityByClassId")
    ResultVO getALLActivityByClassId(@RequestParam("classId") int classId,@RequestParam("pageIndex") int pageIndex){
        return circleClassService.getALLActivityByClassId(classId,pageIndex);
    }

    /**
     * 所有获取圈子的信息
     * @return 分页后的圈子集合
     */
    @RequestMapping("getAllCircleClassInfo")
    ResultVO getAllCirclIndex(){
        return circleClassService.getAllCircleClassIndo();
    }

    /**
     * 获取圈子信息
     * @param classId 圈子Id
     * @return 圈子信息
     */
    @RequestMapping("getCircleInfo")
    ResultVO getCircleInfo(int classId){ return  circleClassService.getCircle(classId); }

    /**
     * 删除圈子
     * @param classId 圈子Id
     * @return 结果
     */
    @RequestMapping("deleteCircleByClassId")
    ResultVO deleteCircleByClassId(int classId){return circleClassService.deleteCircle(classId);}

    /**
     * 更新圈子信息
     * @param circleClass 圈子信息
     * @return 结果
     */
    @RequestMapping("updateCirclrInfo")
    ResultVO updateCirclrInfo(CircleClass circleClass){
        return  circleClassService.updateCircleInfo(circleClass);
    }
}
