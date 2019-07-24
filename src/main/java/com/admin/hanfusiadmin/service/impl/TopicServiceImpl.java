package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.TopicDao;
import com.admin.hanfusiadmin.entity.TopicTable;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.form.TopicForm;
import com.admin.hanfusiadmin.service.TopicService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import com.admin.hanfusiadmin.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicDao topicDao;

    /**
     * 分页获取话题列表
     * @param pageIndex 当前页
     * @return 当前页的话题列表
     */
    @Override
    public ResultVO getAllTopic(int pageIndex) {
        int totalCount = topicDao.getAllTopicCount();
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }
        pageIndex = (pageIndex - 1) * 6;
        List<TopicTable> topicTables = topicDao.getAllTopic(pageIndex);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("topicTables", topicTables);
        return ResultVOUtil.success(map);
    }

    /**
     * 添加新话题
     * @param topicForm 新话题信息
     * @return 添加结果
     */
    @Override
    public ResultVO addTopic(TopicForm topicForm) {
        TopicTable topicTable = new TopicTable();
        topicTable.setTopicName(topicForm.getTopicName());
        topicTable.setDynamicId(topicForm.getDynamicId());
        topicTable.setTopicNum(topicForm.getTopicNum());
        topicTable.setTypes(topicForm.getTypes());
        int row;
        if(topicForm.getFile() != null){
            topicTable.setTopicImg( UploadFileUtil.uploadFile(topicForm.getFile(), "fictitious"));
            row = topicDao.addTopicByImg(topicTable);
        }else{
            row = topicDao.addTopic(topicTable);
        }
        return  row > 0 ? ResultVOUtil.success() : ResultVOUtil.error(500000,"添加话题失败请检查输入是否有误？");
    }

    /**
     * 删除话题
     * @param topicId 话题id
     * @return 删除结果
     */
    @Override
    public ResultVO deleteTopicById(int topicId) {
        System.out.println("12345");
        topicDao.deleteDynamicById(String.valueOf(topicId));
        return topicDao.deleteTopicById(topicId) > 0 ? ResultVOUtil.success() : ResultVOUtil.error(50000,"删除话题失败");
    }

    /**
     * 话题置顶 或取消置顶
     * @param topicId 话题Id
     * @param sort 置顶字段
     * @return 结果
     */
    @Override
    public ResultVO updateSort(int topicId, int sort) {

        return topicDao.updateSort(topicId,sort) > 0 ?
                ResultVOUtil.success():ResultVOUtil.error(50000,"操作失败");
    }
}
