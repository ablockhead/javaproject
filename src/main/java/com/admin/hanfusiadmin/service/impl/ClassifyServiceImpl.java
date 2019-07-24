package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.OfficialTopicVO;
import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.ChildLogDao;
import com.admin.hanfusiadmin.dao.ClassifyDao;
import com.admin.hanfusiadmin.dao.TopicDao;
import com.admin.hanfusiadmin.dao.UserDao;
import com.admin.hanfusiadmin.entity.*;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.form.ActivityForm;
import com.admin.hanfusiadmin.form.AddClassifyForm;
import com.admin.hanfusiadmin.form.EditFeilei;
import com.admin.hanfusiadmin.form.SocietiesForm;
import com.admin.hanfusiadmin.service.ClassifyService;
import com.admin.hanfusiadmin.utils.FastDFSClient;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import com.admin.hanfusiadmin.utils.TokenUtil;
import com.admin.hanfusiadmin.utils.UploadFileUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyDao classifyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TopicDao topicDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ChildLogDao childLogDao;

    /**
     * 获取分类列表
     * @return 列表
     */
    @Override
    public ResultVO getClassifyList() {
        return ResultVOUtil.success(classifyDao.getClassifyList());
    }

    /**
     * 获取全部分类列表
     * @param pageIndex 当前页
     * @return 第index分类列表
     */
    @Override
    public ResultVO getAllClassifyList(int pageIndex) {
        int totalCount = classifyDao.getClassifyCount();
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }

        pageIndex = (pageIndex - 1) * 6;
        List<Classify> classifyList = classifyDao.getAllClassifyList(pageIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("classifyList", classifyList);

        return ResultVOUtil.success(map);
    }

    /**
     * 添加新分类
     * @param addClassifyForm 新分类信息
     * @return 结果
     */
    @Override
    public ResultVO addClassifyInfo(AddClassifyForm addClassifyForm) {
        classifyDao.updateSortAdd(addClassifyForm.getSort());
        String url = UploadFileUtil.uploadFile(addClassifyForm.getFile(), "fictitious");
        Classify classify = new Classify();
        classify.setSort(addClassifyForm.getSort());
        classify.setTitle(addClassifyForm.getTitle());
        classify.setPages(addClassifyForm.getPages());
        classify.setUrl(url);
        int row = classifyDao.addClassifyDao(classify);
        if(row > 0){
            return ResultVOUtil.success();
        }
        return ResultVOUtil.error(20004,"添加新分类失败");
    }

    /**
     * 删除分类
     * @param classifyId 要删除的分类id
     * @return 结果
     */
    @Override
    @CacheEvict(cacheNames = "classify", allEntries = true)
    public ResultVO deleteClassifyById(int classifyId,int sort) {
        classifyDao.updateSortDelete(sort);
        int row = classifyDao.deleteClassify(classifyId);
        if(row > 0){
            return  ResultVOUtil.success();
        }else{
            return  ResultVOUtil.error(200004,"删除分类失败");
        }
    }

    /**
     * 获取分类信息
     * @param classifyId 分类id
     * @return 分类信息
     */
    @Override
    public ResultVO getClassify(Integer classifyId) {
        return ResultVOUtil.success(classifyDao.getClassify(classifyId));
    }

    /**
     * 修改分类
     * @param officialTopicVO 官方话题修改参数
     * @return 结果
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = "classify", allEntries = true)
    public ResultVO updateClassify(MultipartHttpServletRequest request, OfficialTopicVO officialTopicVO) {
        Dynamic dynamic = new Dynamic();
        dynamic.setOpenId("hanfusi");
        dynamic.setContent(officialTopicVO.getContent());
        int row = classifyDao.addOfficialDyname(dynamic);
        if (row <= 0){
            log.error("插入官方动态失败，{}", officialTopicVO);
            throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
        }

        officialTopicVO.setDynamicId(dynamic.getDynamicId());
        OfficialTopicVO officialTopic = classifyDao.getClassify(officialTopicVO.getClassifyId());

        officialTopicVO.setUrl(officialTopic.getUrl());
        officialTopicVO.setTopicImg(officialTopic.getTopicImg());
        Map<String, MultipartFile> map = request.getFileMap();
        if (map.size() > 0){
            for(String mapName: map.keySet()){
                if (mapName.equals("urlFile")){
                    MultipartFile multipartFile = map.get(mapName);
                    officialTopicVO.setUrl(UploadFileUtil.uploadFile(multipartFile, "common"));
                    File file = new File(officialTopic.getUrl());
                    if (file.exists()){
                        file.delete();
                    }
                }
                if (mapName.equals("topicImgFile")){
                    MultipartFile multipartFile = map.get(mapName);
                    officialTopicVO.setTopicImg(UploadFileUtil.uploadFile(multipartFile, "topic"));
                    File file = new File(officialTopic.getTopicImg());
                    file.delete();
                }
            }
        }

        row = classifyDao.updateClassify(officialTopicVO);
        if (row <= 0){
            log.error("修改分类失败, {}", officialTopicVO);
            throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
        }

        row = classifyDao.updateOriginalTopic(officialTopic.getTitle());
        if (row <= 0){
            log.error("修改原来的话题失败， {}", officialTopicVO);
            throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
        }

        TopicTable topicTable = classifyDao.getTopic(officialTopicVO.getTitle());
        if (topicTable == null){
            row = classifyDao.addOfficialTopic(officialTopicVO);
            if (row <= 0){
                log.error("添加话题失败, {}", officialTopicVO);
                throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
            }
        } else {
            row = classifyDao.updateOfficialTopic(officialTopicVO);
            if (row <= 0){
                log.error("修改话题失败, {}", officialTopicVO);
                throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
            }
        }

        return ResultVOUtil.success();
    }

    @Override
    public ResultVO updateFenlei(EditFeilei editFeilei) {
        if(editFeilei.getFile() == null){
            return classifyDao.updateClassifyNo(editFeilei.getTitle(),editFeilei.getClassifyId()) > 0 ?
                    ResultVOUtil.success(): ResultVOUtil.error(5000,"修改失败");
        }else{
            String url = UploadFileUtil.uploadFile(editFeilei.getFile(), "fictitious");
            return classifyDao.updateClassifyImg(url,editFeilei.getTitle(),editFeilei.getClassifyId()) > 0 ?
                    ResultVOUtil.success(): ResultVOUtil.error(5000,"修改失败");
        }
    }

    /**
     * 获取活动列表
     * @param pageIndex 当前页
     * @return 活动列表
     */
    @Override
    public ResultVO getActivityList(Integer pageIndex,String adminName) {
        int totalCount = 0;
        if(adminName.equals("admin")){
            totalCount = classifyDao.getActivityCount();
        }else{
            totalCount = childLogDao.getActivityCount(2,adminName);
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
        List<Activity> activityList = adminName.equals("admin") ?
                                classifyDao.getActivityList(pageIndex):
                                classifyDao.getChildActivityList(pageIndex,adminName);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("activityList", activityList);
        return ResultVOUtil.success(map);
    }

    /**
     * 添加活动
     * @param activityForm 活动参数
     * @return 结果
     */
    @Override
    public ResultVO addActivity(ActivityForm activityForm) {
        String url = UploadFileUtil.uploadFile(activityForm.getActivityFile(), "activity");
        activityForm.setActivityImg(url);
        int row = classifyDao.addActivity(activityForm);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.ADD_ERROR);
        }
        ChildLog childLog = new ChildLog();
        childLog.setChildName(activityForm.getAdminName());
        childLog.setHandleDetail("添加了"+activityForm.getActivityName()+"活动");
        childLog.setTypes(2);
        childLog.setTypesId(activityForm.getActivityId());
        childLogDao.addChildLog(childLog);
        return ResultVOUtil.success(activityForm.getActivityId());
    }

    /**
     * 删除活动
     * @param activityId 活动id
     * @return 结果
     */
    @Override
    public ResultVO delActivity(Integer activityId) {
        Activity activity = classifyDao.getActivity(activityId);
        if (activity == null){
            throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
        }
        int row = classifyDao.delActivity(activityId);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.DEL_ERROR);
        }
        File file = new File(activity.getActivityImg());
        if (file.exists()){
            file.delete();
        }

        return ResultVOUtil.success();
    }

    /**
     * 获取线下活动列表
     * @return 列表
     */
    @Override
    public ResultVO getUnderTheLine() {
        return ResultVOUtil.success(classifyDao.getUnderTheLine());
    }

    /**
     * 获取活动报名用户列表
     * @param activityId 活动id
     * @param pageIndex 当前页
     * @return 用户列表
     */
    @Override
    public ResultVO getUnderTheLineUser(Integer activityId, Integer pageIndex) {
        return null;
    }

    /**
     * 通过id获取分类
     * @param classifyId 分类Id
     * @return 分类对象
     */
    @Override
    public ResultVO getClassifyById(int classifyId) {
        return ResultVOUtil.success(classifyDao.getClassifyById(classifyId));
    }

    @Override
    public ResultVO getSheTuanXingzhi(String childName) {
        return ResultVOUtil.success(
                childName.equals("admin")?
                        classifyDao.getSheTuanXingzhi():
                        classifyDao.getChildSheTuanXingzhi(childName)

                );
    }

    /**
     * 添加社团或形制
     * @param societiesForm 添加信息
     * @return 结果
     */
    @Override
    @Transactional
    public ResultVO addSheTuanXingzhi(SocietiesForm societiesForm) {
        // /home/ftp/www/image/openid
        TopicTable topicTable = new TopicTable();
        topicTable.setTypes(societiesForm.getTypes());
        topicTable.setTopicName(societiesForm.getTopicName());
        topicTable.setTopicImg(UploadFileUtil.uploadFile(societiesForm.getTopicImg(), "home/ftp/www/image"));
        classifyDao.addTopic(topicTable);
        // 添加话题的Id
        Long topicId = topicTable.getTopicId();
        String openId = "";
        if(societiesForm.getTypes() == 2){
            // 形制添加 openId 为hanfusi
            openId = "hanfusi";
            ChildLog childLog = new ChildLog();
            childLog.setChildName(societiesForm.getAdminName());
            childLog.setHandleDetail("添加了"+societiesForm.getTopicName()+"形制话题");
            childLog.setTypes(1);
            childLog.setTypesId(topicId);
            childLogDao.addChildLog(childLog);

        }else{
            // 社团添加 先添加用户
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(societiesForm.getUserId());
            userInfo.setOpenId(societiesForm.getOpenId());
            userInfo.setNickName(societiesForm.getNickName());
            userInfo.setAvatarUrl(UploadFileUtil.uploadFile(societiesForm.getTopicImg(), "home/ftpuser/www/images"));
            userDao.addUser(userInfo);
            openId = societiesForm.getOpenId();
            ChildLog childLog = new ChildLog();
            childLog.setChildName(societiesForm.getAdminName());
            childLog.setHandleDetail("添加了"+societiesForm.getTopicName()+"社团");
            childLog.setTypes(1);
            childLog.setTypesId(topicId);
            childLogDao.addChildLog(childLog);
        }
        // 发布动态
        Dynamic dynamic = new Dynamic();
        dynamic.setOpenId(openId);
        dynamic.setContent(societiesForm.getContent());
        dynamic.setTopicId(topicId.toString());
        classifyDao.addDyname(dynamic);
        String rqCode = "https://luoshuchuanmei.com/"+ErWerma(dynamic.getDynamicId()); //二维码地址
        classifyDao.updateDynamic(rqCode,dynamic.getDynamicId());

        return topicDao.updateDynamicId(dynamic.getDynamicId(),topicId.toString()) > 0 ?
                ResultVOUtil.success(): ResultVOUtil.error(5000,"添加失败");
    }

    /**
     * 生成二维码
     * @param rpSNo
     * @return
     */
    public String ErWerma(long rpSNo) {
        RestTemplate rest = new RestTemplate();
        //调用微信接口
        String token = stringRedisTemplate.opsForValue().get("token");
        if (StringUtils.isEmpty(token)){
            JSONObject jsonObject = TokenUtil.requestToken();
            token = (String) jsonObject.get("access_token");
            stringRedisTemplate.opsForValue().set("token", token, 7000, TimeUnit.SECONDS);
        }
        String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token;
        Map<String, Object> param = new HashMap<>();
        param.put("scene", rpSNo);
        //param.put("page", "pages/circleIntro/circleIntro");
        param.put("page", "pages/load/load");
        Map<String, Object> line_color = new HashMap<>();
        line_color.put("r", 43);
        line_color.put("g", 162);
        line_color.put("b", 70);
        param.put("line_color", line_color);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        HttpEntity requestEntity = new HttpEntity(param, headers);
        ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
//        ResponseEntity<String> s = rest.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
//        System.out.println(s);
        byte[] result = entity.getBody();
        InputStream inputStream = new ByteArrayInputStream(result);
        String erweimaname = FastDFSClient.UploadFile(inputStream);
        return erweimaname;
    }
}
