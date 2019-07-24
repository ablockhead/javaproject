package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ImagesVO;
import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.VO.UserDynamicVO;
import com.admin.hanfusiadmin.VO.UserVO;
import com.admin.hanfusiadmin.dao.UserDao;
import com.admin.hanfusiadmin.entity.AdminUser;
import com.admin.hanfusiadmin.entity.Dynamic;
import com.admin.hanfusiadmin.entity.TopicTable;
import com.admin.hanfusiadmin.entity.UserInfo;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.form.EditUserInfo;
import com.admin.hanfusiadmin.form.FictitiousDynamic;
import com.admin.hanfusiadmin.form.FictitiousUser;
import com.admin.hanfusiadmin.service.UserService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import com.admin.hanfusiadmin.utils.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ResultVO login(String userName, String password) {
        AdminUser adminUser = userDao.getAdminUser(userName);
        if (adminUser == null){
            throw new HanfusiException(HanfusiEnums.USERNAME_ERROR);
        }
        if (!adminUser.getPassword().equals(password)){
            throw new HanfusiException(HanfusiEnums.PASSWORD_ERROR);
        }

        return ResultVOUtil.success(adminUser);
    }

    /**
     * 添加子管理员
     * @param adminUser 子管理员信息
     * @return 结果
     */
    @Override
    public ResultVO addChildAdmin(AdminUser adminUser) {

        return userDao.addChildAdmin(adminUser) > 0 ?
                ResultVOUtil.success() : ResultVOUtil.error(5000,"添加子管理失败请稍后重试");
    }

    /**
     * 查询所有子账户
     * @return 子账户集合
     */
    @Override
    public ResultVO getAllChildAdmin() {
        return ResultVOUtil.success(userDao.getAllChildAdmin());
    }

    /**
     * 获取用户列表
     * @param nickName 昵称
     * @param pageIndex 当前页数
     * @return 用户列表
     */
    @Override
    public ResultVO getUserInfoList(String nickName, Integer pageIndex) {
        int totalCount = userDao.getUserCount(nickName);
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }

        pageIndex = (pageIndex - 1) * 6;
        List<UserInfo> userInfoList = userDao.getUserInfoList(nickName, pageIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("userInfoList", userInfoList);

        return ResultVOUtil.success(map);
    }

    /**
     * 修改汉服币
     * @param openId 用户openId
     * @param currency 汉服币
     * @return 修改结果
     */
    @Override
    public ResultVO updateCurrency(String openId, Integer currency) {
        if (currency == null){
            currency = 0;
        }
        int row = userDao.updateCurrency(openId, currency);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     * 获取用户信息
     * @param openId 用户openId
     * @return 用户信息
     */
    @Override
    public ResultVO getUserInfo(String openId) {
        UserVO userVO = userDao.getUserInfo(openId);
        userVO.setFollowNum(userDao.getFollowNum(openId));
        userVO.setFansNum(userDao.getFansNum(openId));
        return ResultVOUtil.success(userVO);
    }

    /**
     * 用户发布的动态
     * @param openId 用户openId
     * @param pageIndex 当前页
     * @return 动态列表
     */
    @Override
    public ResultVO myDynamicList(String openId, Integer pageIndex) {
        int totalCount = userDao.getMyDynamicCount(openId);
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }

        pageIndex = (pageIndex - 1) * 6;
        List<UserDynamicVO> myDynamicList = userDao.getMyDynamicList(openId, pageIndex);
        for (UserDynamicVO userDynamicVO: myDynamicList){
            // 获取动态对应的图片
            List<ImagesVO> imageUrlList = userDao.getImageUrlList(userDynamicVO.getDynamicId());
            userDynamicVO.setImageUrl(imageUrlList);

            // 点赞数量
            int likeNum = userDao.getLikeNum(userDynamicVO.getDynamicId());
            userDynamicVO.setLikeNum(likeNum);

            // 评论数量（包括回复）
            int commentNum = userDao.getCommentNum(userDynamicVO.getDynamicId());
            userDynamicVO.setCommentNum(commentNum);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("myDynamicList", myDynamicList);
        return ResultVOUtil.success(map);
    }

    /**
     * 添加虚拟用户
     * @param fictitiousUser 虚拟用户参数
     * @return 结果
     */
    @Override
    public ResultVO addFictitiousUser(FictitiousUser fictitiousUser) {
        UserInfo userInfo = userDao.getUserInfoByUserId(fictitiousUser.getUserId());
        if (userInfo != null){
            throw new HanfusiException(-1, "添加失败，userId已经存在");
        }
        userInfo = userDao.getUserInfoByOpenId(fictitiousUser.getOpenId());
        if (userInfo != null){
            throw new HanfusiException(-1, "添加失败，openId已经存在");
        }

        String avatarUrl = UploadFileUtil.uploadFile(fictitiousUser.getFile(), "fictitious");
        userInfo = new UserInfo();
        userInfo.setUserId(fictitiousUser.getUserId());
        userInfo.setOpenId(fictitiousUser.getOpenId());
        userInfo.setNickName(fictitiousUser.getNickName());
        userInfo.setAvatarUrl(avatarUrl);
        userInfo.setLabel(fictitiousUser.getLabel());
        userInfo.setTypes(fictitiousUser.getTypes());
        int row = userDao.addFictitiousUser(userInfo);
        if (row <= 0){
            File file = new File(avatarUrl);
            if (file.exists()){
                file.delete();
            }
            throw new HanfusiException(HanfusiEnums.ADD_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     *  修改用户信息
     * @param editUserInfo 用户参数
     * @return 结果
     */
    @Override
    public ResultVO editUserInfo(EditUserInfo editUserInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(editUserInfo.getNickName());
        userInfo.setLabel(editUserInfo.getLabel());
        userInfo.setOpenId(editUserInfo.getOpenId());
        userInfo.setCurrency(editUserInfo.getCurrency());
        userInfo.setLatitude(editUserInfo.getLatitude());
        userInfo.setLongitude(editUserInfo.getLongitude());
        userInfo.setState(editUserInfo.getState());
        if(editUserInfo.getFile() != null){
            // 加头像更改
            userInfo.setAvatarUrl(UploadFileUtil.uploadFile(editUserInfo.getFile(), "fictitious"));
            int row = userDao.editUserInfoWithFile(userInfo);
                if(row > 0){
                return ResultVOUtil.success();
            }
        }else{
            int row = userDao.editUserInfo(userInfo);
            if(row > 0){
                return ResultVOUtil.success();
            }
        }

        return ResultVOUtil.error(200004,"'更改用户信息失败'");
    }

    /**
     * 删除虚拟用户
     * @param openId 用户的openId
     * @return 结果
     */
    @Override
    public ResultVO delLikeUser(String openId) {
        int row = userDao.delLikeUser(openId);
       return  row > 0 ? ResultVOUtil.success() : ResultVOUtil.error(50000,"删除用户失败");
    }

    /**
     * 添加虚拟动态
     * @param request 请求（用于获取上传多个文件）
     * @param fictitiousDynamic 虚拟动态参数
     * @return 结果
     */
    @Override
    @Transactional
    public ResultVO addFictitiousDynamic(MultipartHttpServletRequest request, FictitiousDynamic fictitiousDynamic) {
        // 遍历话题
        List<String> topicList = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(fictitiousDynamic.getContent());
        while (matcher.find()){
            String topic = matcher.group().substring(1, matcher.group().length() - 1);
            topic = topic.trim().replaceAll(" ", "");
            if (StringUtils.isNotEmpty(topic)){
                boolean flag = true;
                for (String topicStr: topicList){
                    if (topic.equals(topicStr)){
                        flag = false;
                    }
                }
                if (flag){
                    topicList.add(topic);
                }
            }
        }

        // 添加话题或者修改话题次数
        StringBuffer topicId = new StringBuffer();
        for (String topic: topicList){
            TopicTable topictable = userDao.getTopic(topic);
            if (topictable == null){
                topictable = new TopicTable();
                topictable.setTopicName(topic);
                int row = userDao.addTopic(topictable);
                if (row <= 0){
                    log.error("添加话题失败");
                    throw new HanfusiException(HanfusiEnums.ADD_ERROR);
                }
                topicId.append(topictable.getTopicId());
                topicId.append(",");
            } else {
                int row = userDao.updateTopic(topictable);
                if (row <= 0){
                    log.error("添加话题次数失败");
                    throw new HanfusiException(HanfusiEnums.ADD_ERROR);
                }
                topicId.append(topictable.getTopicId());
                topicId.append(",");
            }
        }

        Dynamic dynamic = new Dynamic();
        dynamic.setOpenId(fictitiousDynamic.getOpenId());
        dynamic.setContent(fictitiousDynamic.getContent());
        dynamic.setTopicId(topicId.toString());
        dynamic.setLocation(fictitiousDynamic.getLocation());
        dynamic.setLatitude(fictitiousDynamic.getLatitude());
        dynamic.setLongitude(fictitiousDynamic.getLongitude());
        int row = userDao.send(dynamic);
        if (row <= 0){
            log.error("发布动态失败");
            throw new HanfusiException(HanfusiEnums.ADD_ERROR);
        }

        Map<String,MultipartFile> map = request.getFileMap();
        if (map.size() > 0){
            for(String mapName: map.keySet()){
                MultipartFile file = map.get(mapName);
                String url = UploadFileUtil.uploadFile(file, "dynamic" + dynamic.getDynamicId());
                if (fictitiousDynamic.getPhotoType() == 1){
                    row = userDao.addPhoto(dynamic.getDynamicId(), url, "", fictitiousDynamic.getPhotoType());
                } else if (fictitiousDynamic.getPhotoType() == 2){
                    String coverUrl = UploadFileUtil.screenshot(url);
                    row = userDao.addPhoto(dynamic.getDynamicId(), url, coverUrl, fictitiousDynamic.getPhotoType());
                }
                if (row <= 0){
                    log.error("发动态图片插入数据库失败");
                    throw new HanfusiException(HanfusiEnums.ADD_ERROR);
                }
            }
        }

        return ResultVOUtil.success();
    }

    /**
     * 删除虚拟动态
     * @param dynamicId 动态id
     * @return 结果
     */
    @Override
    public ResultVO delFictitiousDynamic(long dynamicId) {
        int row = userDao.delFictitiousDynamic(dynamicId);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.DEL_ERROR);
        }
        List<ImagesVO> imagesVOList = userDao.getImageUrlList(dynamicId);
        for (ImagesVO imagesVO: imagesVOList){
            File f = new File(imagesVO.getUrl());
            if (f.exists()){
                f.delete();
            }
            f = new File(imagesVO.getCoverUrl());
            if (f.exists()){
                f.delete();
            }
        }
        userDao.delPhoto(dynamicId);
        userDao.delAtFriends(dynamicId);
        userDao.delCommentByDynamicId(dynamicId);
        userDao.delReplyByDynamicId(dynamicId);
        userDao.delLikeByDynamicId(dynamicId);

        return ResultVOUtil.success();
    }

    /**
     * 添加点赞数量
     * @param dynamicId 动态id
     * @param likeNum 要修改的数量
     * @return 结果
     */
    @Override
    public ResultVO addFictitiousLike(String openId, long dynamicId, Integer likeNum) {
        List<String> randomList = new ArrayList<>();
        for (int i = 0; i < likeNum; i++){
            randomList.add(String.valueOf((int)((Math.random() * 9000) + 1000)) + String.valueOf(System.nanoTime() % 1000000) + String.valueOf((int)((Math.random() * 9000) + 1000)));
        }
        userDao.addFictitiousLike(openId, dynamicId, randomList);

        return ResultVOUtil.success();
    }

    /**
     * 获取 参加某个活动的人信息
     * @param activityId 活动Id
     * @return 用户信息集合
     */
    @Override
    public ResultVO getPeopleByactivityId(int activityId) {
        List<UserInfo> userInfos = userDao.getPeopleByactivityId(activityId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userinfos",userInfos);
        map.put("countNum",userInfos.size());
        return ResultVOUtil.success(map);
    }

    /**
     * 置顶或取消置顶
     * @param dynamicId 动态Id
     * @param sort 1为置顶9为正常
     * @return 结果
     */
    @Override
    public ResultVO updateSort(long dynamicId, int sort) {

        return userDao.updateSort(dynamicId,sort) > 0 ?
                ResultVOUtil.success():ResultVOUtil.error(5000,"操作失败请稍后重试");
    }


    @Override
    public ResultVO deleteUserInfo(String openId) {
        userDao.deleteUserInfo(openId);
        return ResultVOUtil.success();
    }
}
