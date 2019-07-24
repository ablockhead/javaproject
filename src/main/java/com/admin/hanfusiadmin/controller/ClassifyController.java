package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.OfficialTopicVO;
import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.form.ActivityForm;
import com.admin.hanfusiadmin.form.AddClassifyForm;
import com.admin.hanfusiadmin.form.EditFeilei;
import com.admin.hanfusiadmin.form.SocietiesForm;
import com.admin.hanfusiadmin.service.ClassifyService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("classify")
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    @RequestMapping("getSheTuanXingzhi")
    public ResultVO getSheTuanXingzhi(String childName) {
        return classifyService.getSheTuanXingzhi(childName);
    }

    /**
     * 获取分类列表
     * @return 列表
     */
    @RequestMapping("getClassifyList")
    public ResultVO getClassifyList(){
        return classifyService.getClassifyList();
    }

    /**
     * 获取全部分类列表
     * @return 列表
     */
    @RequestMapping("getAllClassifyList")
    public ResultVO getAllClassifyList(@RequestParam("pageIndex") int PageIndex){
        return classifyService.getAllClassifyList(PageIndex);
    }

    /**
     * 获取分类信息
     * @param classifyId 分类id
     * @return 分类信息
     */
    @RequestMapping("getClassify")
    public ResultVO getClassify(@RequestParam("classifyId") Integer classifyId){
        return classifyService.getClassify(classifyId);
    }

    /**
     * 通过id获取分类
     * @param classifyId 分类Id
     * @return 分类对象
     */
    @RequestMapping("getClassifyById")
    public ResultVO getClassifyById(int classifyId){
        return classifyService.getClassifyById(classifyId);
    }

    /**
     * 添加新分类
     * @param addClassifyForm 新分类信息
     * @return 结果
     */
    @RequestMapping("addClassify")
    public ResultVO addClassify(AddClassifyForm addClassifyForm){
        return  classifyService.addClassifyInfo(addClassifyForm);
    }

    @RequestMapping("updateFenlei")
    public ResultVO updateFenlei(EditFeilei editFeilei){
        return classifyService.updateFenlei(editFeilei);
    }

    /**
     * 删除分类
     * @param classifyId 要删除的分类Id
     * @return 结果
     */
    @RequestMapping("deleteClassify")
    public ResultVO deleteClassify(@RequestParam("classifyId") int classifyId,@RequestParam("sort") int sort){
        return  classifyService.deleteClassifyById(classifyId,sort);
    }


    /**
     * 修改分类
     * @param officialTopicVO 官方话题修改参数
     * @return 结果
     */
    @RequestMapping("updateClassify")
    public ResultVO updateClassify(MultipartHttpServletRequest request, OfficialTopicVO officialTopicVO){
        return classifyService.updateClassify(request, officialTopicVO);
    }

    /**
     * 获取活动列表
     * @param pageIndex 当前页
     * @return 活动列表
     */
    @RequestMapping("getActivityList")
    public ResultVO getActivityList(@RequestParam("pageIndex") Integer pageIndex,@RequestParam("adminName") String adminName){
        return classifyService.getActivityList(pageIndex,adminName);
    }

    /**
     * 添加活动
     * @param activityForm 活动参数
     * @return 结果
     */
    @RequestMapping("addActivity")
    public ResultVO addActivity(ActivityForm activityForm){
        return classifyService.addActivity(activityForm);
    }

    /**
     * 删除活动
     * @param activityId 活动id
     * @return 结果
     */
    @RequestMapping("delActivity")
    public ResultVO delActivity(Integer activityId){
        return classifyService.delActivity(activityId);
    }

    /**
     * 获取线下活动列表
     * @return 列表
     */
    @RequestMapping("getUnderTheLine")
    public ResultVO getUnderTheLine(){
        return classifyService.getUnderTheLine();
    }

    /**
     * 获取活动报名用户列表
     * @param activityId 活动id
     * @param pageIndex 当前页
     * @return 用户列表
     */
    @RequestMapping("getUnderTheLineUser")
    public ResultVO getUnderTheLineUser(@RequestParam("activityId") Integer activityId, @RequestParam("pageIndex") Integer pageIndex){
        return classifyService.getUnderTheLineUser(activityId, pageIndex);
    }

    /**
     * 添加社团或形制
     * @param societiesForm 添加信息
     * @return 结果
     */
    @RequestMapping("addSheTuanXingzhi")
    public ResultVO addSheTuanXingzhi(SocietiesForm societiesForm){
        return classifyService.addSheTuanXingzhi(societiesForm);
    }
}
