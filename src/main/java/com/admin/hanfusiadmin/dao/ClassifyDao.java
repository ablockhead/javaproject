package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.VO.OfficialTopicVO;
import com.admin.hanfusiadmin.entity.*;
import com.admin.hanfusiadmin.form.ActivityForm;
import com.admin.hanfusiadmin.form.SocietiesForm;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ClassifyDao {

    @Select("SELECT * FROM classify WHERE classifyId != 1 AND classifyId != 2 AND classifyId != 4 AND classifyId != 5 ORDER BY classifyId ASC")
    List<Classify> getClassifyList();

    @Select("SELECT * FROM classify ORDER BY sort ASC LIMIT #{pageIndex},6")
    List<Classify> getAllClassifyList(int pageIndex);

    @Select("SELECT count(1) from classify ")
    int getClassifyCount();

    @Select("SELECT * from classify WHERE classifyId = #{classifyId}")
    Classify getClassifyById(int classifyId);


    @Update("UPDATE classify set sort = sort + 1 WHERE sort >= #{sort}")
    void updateSortAdd(int sort);

    @Update("UPDATE classify set sort = sort - 1 WHERE sort < #{sort}")
    void updateSortDelete(int sort);

    @Insert("INSERT INTO classify (url,pages,sort,title) values(#{url},#{pages},#{sort},#{title})")
    int addClassifyDao(Classify classify);

    @Delete("Delete FROM classify WHERE classifyId = #{classifyId}")
    int deleteClassify(@Param("classifyId")int classifyId);

    @Select("SELECT c.classifyId, c.title, c.url, t.topicImg, d.dynamicId, d.content FROM classify c JOIN topictable t " +
            "ON c.title = t.topicName JOIN dynamic d ON t.dynamicId = d.dynamicId WHERE c.classifyId = #{classifyId}")
    OfficialTopicVO getClassify(Integer classifyId);

    @Insert("INSERT INTO dynamic (openId, content) VALUES (#{openId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "dynamicId", keyColumn = "dynamicId")
    int addOfficialDyname(Dynamic dynamic);

    @Insert("INSERT INTO dynamic (openId,topicId, content) VALUES (#{openId}, #{topicId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "dynamicId", keyColumn = "dynamicId")
    int addDyname(Dynamic dynamic);

    @Update("UPDATE classify SET title = #{title}, url = #{url} WHERE classifyId = #{classifyId}")
    int updateClassify(OfficialTopicVO officialTopicVO);

    @Update("UPDATE classify SET title = #{title}, url = #{url} WHERE classifyId = #{classifyId}")
    int updateClassifyImg(@Param("url") String url,@Param("title") String title,@Param("classifyId") int classifyId);

    @Update("UPDATE classify SET title = #{title} WHERE classifyId = #{classifyId}")
    int updateClassifyNo(@Param("title") String title,@Param("classifyId") int classifyId);

    @Update("UPDATE topictable SET topicImg = '', dynamicId = 0, types = 0 WHERE topicName = #{topicName}")
    int updateOriginalTopic(String topicName);

    @Select("SELECT * FROM topictable WHERE topicName = #{topicName}")
    TopicTable getTopic(String topicName);

    @Insert("INSERT INTO topictable (topicName, topicNum, topicImg, dynamicId, types) VALUE (#{title}, 0, #{topicImg}, #{dynamicId}, 1)")
    int addOfficialTopic(OfficialTopicVO officialTopicVO);

    @Insert("UPDATE topictable SET topicImg = #{topicImg}, dynamicId = #{dynamicId}, types = 1 WHERE topicName = #{title}")
    int updateOfficialTopic(OfficialTopicVO officialTopicVO);

    @Select("SELECT count(*) FROM activity")
    int getActivityCount();

    @Select("SELECT * FROM activity ORDER BY createTime DESC LIMIT #{pageIndex}, 6")
    List<Activity> getActivityList(Integer pageIndex);

    @Select("SELECT a.* FROM activity a JOIN childlog c ON a.activityId = c.typesId WHERE c.types = 2 AND c.childName = #{childName} ORDER BY a.createTime DESC LIMIT #{pageIndex}, 6")
    List<Activity> getChildActivityList(@Param("pageIndex") Integer pageIndex,@Param("childName") String childName);

    @Insert("INSERT INTO activity (activityName, activityImg, activityNotice, signUpFee, activityLocation, longitude, " +
            "latitude, types, startTime, endTime, activityTime) VALUE (#{activityName}, #{activityImg}, #{activityNotice}, " +
            "#{signUpFee}, #{activityLocation}, #{longitude}, #{latitude}, #{types}, #{startTime}, #{endTime}, #{activityTime})")
    @Options(useGeneratedKeys = true, keyProperty = "activityId", keyColumn = "activityId")
    int addActivity(ActivityForm activityForm);

    @Select("SELECT * FROM activity WHERE activityId = #{activityId}")
    Activity getActivity(Integer activityId);

    @Update("UPDATE activity set status = #{status} WHERE activityId = #{activityId}")
    int updateStatus(int status,int activityId);
    @Delete("DELETE FROM activity WHERE activityId = #{activityId}")
    int delActivity(Integer activityId);

    @Select("SELECT activityId, activityName FROM activity WHERE types = 1 ORDER BY createTime DESC")
    List<Activity> getUnderTheLine();

    @Select("SELECT count(1) FROM signup WHERE activityId = #{activityId}")
    int getSignup(int activityId);

    @Select("SELECT t.topicId,t.topicName,t.topicImg,t.dynamicId,t.types,d.openId,d.content,d.createTime FROM topictable t JOIN dynamic d ON t.dynamicId = d.dynamicId WHERE t.types=2 or t.types=3 ORDER BY t.createTime DESC")
    List<SheTuanXingzhi> getSheTuanXingzhi();

    @Select("SELECT t.topicId,t.topicName,t.topicImg,t.dynamicId,t.types,d.openId,d.content,d.createTime,u.openId FROM topictable t" +
            " JOIN dynamic d ON t.dynamicId = d.dynamicId " +
            "JOIN childlog c ON t.topicId = c.typesId JOIN userinfo u ON d.openId = u.openId WHERE (t.types=2 or t.types=3) AND c.types=1 AND c.childName=#{childName} ORDER BY t.createTime DESC")
    List<SheTuanXingzhi> getChildSheTuanXingzhi(String childName);

    @Insert("INSERT INTO topictable (topicName,topicImg,types) values(#{topicName},#{topicImg},#{types})")
    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="topicId", before=false, resultType=long.class)
    int addTopic(TopicTable topicTable);

    @Update("UPDATE dynamic SET rqCode=#{rqCode} WHERE dynamicId=#{dynamicId}")
    int updateDynamic(@Param("rqCode") String rqCode,@Param("dynamicId") long dynamicId);
}
