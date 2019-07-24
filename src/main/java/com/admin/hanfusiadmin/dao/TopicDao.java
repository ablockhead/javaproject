package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.entity.TopicTable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TopicDao {

    @Select("SELECT * FROM topictable ORDER BY createTime DESC LIMIT #{pageIndex},6")
    List<TopicTable> getAllTopic(int pageIndex);

    @Select("SELECT count(1) FROM topictable")
    int getAllTopicCount();

    @Insert("INSERT INTO topictable (topicName,topicNum,topicImg,dynamicId,types) values (#{topicName},#{topicNum},#{topicImg},#{dynamicId},#{types}) ")
    int addTopicByImg(TopicTable topicTable);

    @Insert("INSERT INTO topictable (topicName,topicNum,dynamicId,types) values (#{topicName},#{topicNum},#{dynamicId},#{types})")
    int addTopic(TopicTable topicTable);

    @Delete("DELETE FROM topictable WHERE topicId = #{topicId}")
    int deleteTopicById(Integer topicId);

    @Delete("DELETE FROM dynamic WHERE topicId = #{topicId}")
    int deleteDynamicById(@Param("topicId") String topicId);

    @Update("UPDATE topictable SET sort = #{sort} WHERE  topicId = #{topicId}")
    int updateSort(@Param("topicId") int topicId,@Param("sort") int sort);

    @Update("UPDATE topictable SET dynamicId = #{dynamicId} WHERE topicId = #{topicId}")
    int updateDynamicId(@Param("dynamicId") long dynamicId,@Param("topicId") String topicId);
}
