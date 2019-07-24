package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.AdminUser;
import com.admin.hanfusiadmin.form.EditUserInfo;
import com.admin.hanfusiadmin.form.FictitiousDynamic;
import com.admin.hanfusiadmin.form.FictitiousUser;
import com.admin.hanfusiadmin.service.UserService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public ResultVO login(String userName, String password){
        return userService.login(userName, password);
    }

    /**
     * 添加子管理员
     * @param adminUser 子管理员信息
     * @return 结果
     */
    @RequestMapping("addChildAdmin")
    public  ResultVO addChildAdmin(AdminUser adminUser){
        return userService.addChildAdmin(adminUser);
    }

    /**
     * 查询所有子账户
     * @return 子账户集合
     */
    @RequestMapping("getAllChildAdmin")
    public ResultVO getAllChildAdmin() {
        return userService.getAllChildAdmin();
    }

    /**
     * 获取用户列表
     * @param nickName 昵称
     * @param pageIndex 当前页数
     * @return 用户列表
     */
    @RequestMapping("getUserInfoList")
    public ResultVO getUserInfoList(@RequestParam(value = "nickName", required = false, defaultValue = "") String nickName,
                                    @RequestParam("pageIndex") Integer pageIndex){
        return userService.getUserInfoList(nickName, pageIndex);
    }

    /**
     * 修改汉服币
     * @param openId 用户openId
     * @param currency 汉服币
     * @return 修改结果
     */
    @RequestMapping("updateCurrency")
    public ResultVO updateCurrency(@RequestParam("openId") String openId, @RequestParam("currency") Integer currency){
        return userService.updateCurrency(openId, currency);
    }

    /**
     * 获取用户信息
     * @param openId 用户openId
     * @return 用户信息
     */
    @RequestMapping("getUserInfo")
    public ResultVO getUserInfo(String openId){
        return userService.getUserInfo(openId);
    }

    /**
     * 用户发布的动态
     * @param openId 用户openId
     * @param pageIndex 当前页
     * @return 动态列表
     */
    @RequestMapping("myDynamicList")
    public ResultVO myDynamicList(@RequestParam("openId") String openId, @RequestParam("pageIndex") Integer pageIndex){
        return userService.myDynamicList(openId, pageIndex);
    }

    /**
     * 添加虚拟用户
     * @param fictitiousUser 虚拟用户参数
     * @return 结果
     */
    @RequestMapping("addFictitiousUser")
    public ResultVO addFictitiousUser(FictitiousUser fictitiousUser){
        return userService.addFictitiousUser(fictitiousUser);
    }

    /**
     * 添加虚拟动态
     * @param request 请求（用于获取上传多个文件）
     * @param fictitiousDynamic 虚拟动态参数
     * @return 结果
     */
    @RequestMapping("addFictitiousDynamic")
    public ResultVO addFictitiousDynamic(MultipartHttpServletRequest request, FictitiousDynamic fictitiousDynamic){
        return userService.addFictitiousDynamic(request, fictitiousDynamic);
    }

    /**
     * 删除虚拟动态
     * @param dynamicId 动态id
     * @return 结果
     */
    @RequestMapping("delFictitiousDynamic")
    public ResultVO delFictitiousDynamic(@RequestParam("dynamicId") long dynamicId){
        return userService.delFictitiousDynamic(dynamicId);
    }

    /**
     * 添加点赞数量
     * @param openId 发布动态的用户openId
     * @param dynamicId 动态id
     * @param likeNum 要修改的数量
     * @return 结果
     */
    @RequestMapping("addFictitiousLike")
    public ResultVO addFictitiousLike(@RequestParam("openId") String openId, @RequestParam("dynamicId") long dynamicId,
                                      @RequestParam("likeNum") Integer likeNum){
        return userService.addFictitiousLike(openId, dynamicId, likeNum);
    }

    /**
     * 修改用户信息
     * @param editUserInfo 用户信息
     * @return 结果
     */
    @RequestMapping("editUserInfo")
    public ResultVO editUserInfo(EditUserInfo editUserInfo){
        System.out.printf("111111");
        return userService.editUserInfo(editUserInfo);
    }

    /**
     * 删除虚拟用户
     * @param openId 虚拟用户openId
     * @return 结果
     */
    @RequestMapping("deleteUser")
    public ResultVO deleteUser(@RequestParam("openId") String openId){
        return  userService.delLikeUser(openId);
    }

    @RequestMapping("getPeopleByactivityId")
    public ResultVO getPeopleByactivityId(int activityId){
        return userService.getPeopleByactivityId(activityId);
    }

    /**
     * 置顶或取消置顶
     * @param dynamicId 动态Id
     * @param sort 1为置顶9为正常
     * @return 结果
     */
    @RequestMapping("updateSort")
    public ResultVO updateSort(@RequestParam("dynamicId") long dynamicId,@RequestParam("sort") int sort) {
        return userService.updateSort(dynamicId,sort);
    }

    @RequestMapping("deleteUserInfo")
    public ResultVO deleteUserInfo(String openId){
     return userService.deleteUserInfo(openId);
    }
}
