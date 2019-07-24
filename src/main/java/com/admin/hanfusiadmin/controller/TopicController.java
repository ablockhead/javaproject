package com.admin.hanfusiadmin.controller;
import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.form.TopicForm;
import com.admin.hanfusiadmin.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    /**
     * 分页获取话题列表
     * @param pageIndex 当前页
     * @return 分页数据
     */
    @RequestMapping("getAllTopic")
    ResultVO getAllTopic(Integer pageIndex){
        return  topicService.getAllTopic(pageIndex);
    }

    /**
     * 添加新话题
     * @param topicForm 新话题信息
     * @return 添加结果
     */
    @RequestMapping("addTopic")
    ResultVO addTopic(TopicForm topicForm){
        return topicService.addTopic(topicForm);
    }

    /**
     * 删除话题
     * @param topicId 话题id
     * @return 删除结果
     */
    @RequestMapping("deleteTopic")
    ResultVO deleteTopic(int topicId){
        return  topicService.deleteTopicById(topicId);
    }

    /**
     * 话题置顶 或取消置顶
     * @param topicId 话题Id
     * @param sort 置顶字段
     * @return 结果
     */
    @RequestMapping("updateSort")
    ResultVO updateSort(@RequestParam("topicId") int topicId,@RequestParam("sort") int sort){
        return topicService.updateSort(topicId,sort);
    }
}
