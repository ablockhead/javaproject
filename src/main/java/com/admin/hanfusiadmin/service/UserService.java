package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.AdminUser;
import com.admin.hanfusiadmin.form.EditUserInfo;
import com.admin.hanfusiadmin.form.FictitiousDynamic;
import com.admin.hanfusiadmin.form.FictitiousUser;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UserService {

    ResultVO login(String userName, String password);

    ResultVO addChildAdmin(AdminUser adminUser);

    ResultVO getAllChildAdmin();

    ResultVO getUserInfoList(String nickName, Integer pageIndex);

    ResultVO updateCurrency(String openId, Integer currency);

    ResultVO getUserInfo(String openId);

    ResultVO myDynamicList(String openId, Integer pageIndex);

    ResultVO addFictitiousUser(FictitiousUser fictitiousUser);

    ResultVO editUserInfo(EditUserInfo editUserInfo);

    ResultVO delLikeUser(String openId);

    ResultVO addFictitiousDynamic(MultipartHttpServletRequest request, FictitiousDynamic fictitiousDynamic);

    ResultVO delFictitiousDynamic(long dynamicId);

    ResultVO addFictitiousLike(String openId, long dynamicId, Integer likeNum);

    ResultVO getPeopleByactivityId(int activityId);

    ResultVO updateSort(long dynamicId,int sort);

    ResultVO deleteUserInfo(String openId);
}
