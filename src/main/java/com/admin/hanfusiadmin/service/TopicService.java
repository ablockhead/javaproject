package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.form.TopicForm;

public interface TopicService {

    ResultVO getAllTopic(int pageIndex);

    ResultVO addTopic(TopicForm topicForm);

    ResultVO deleteTopicById(int topicId);

    ResultVO updateSort(int topicId,int sort);
}
