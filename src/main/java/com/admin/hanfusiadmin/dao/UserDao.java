package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.VO.ImagesVO;
import com.admin.hanfusiadmin.VO.UserDynamicVO;
import com.admin.hanfusiadmin.VO.UserVO;
import com.admin.hanfusiadmin.entity.AdminUser;
import com.admin.hanfusiadmin.entity.Dynamic;
import com.admin.hanfusiadmin.entity.TopicTable;
import com.admin.hanfusiadmin.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface UserDao {

    @Select("SELECT * FROM adminuser WHERE userName = #{userName}")
    AdminUser getAdminUser(String userName);

    @Insert("INSERT INTO adminuser (userName,password,type) values(#{userName},#{password},#{type})")
    int addChildAdmin(AdminUser adminUser);

    @Select("SELECT * FROM adminuser WHERE type = 1")
    List<AdminUser> getAllChildAdmin();

    @Select("<script>SELECT count(*) FROM userinfo WHERE nickName LIKE concat('%', #{nickName}, '%')</script>")
    int getUserCount(String nickName);

    @Select("<script>SELECT * FROM userinfo WHERE nickName LIKE concat('%', #{nickName}, '%') ORDER BY createTime DESC " +
            "LIMIT #{pageIndex}, 6</script>")
    List<UserInfo> getUserInfoList(@Param("nickName") String nickName, @Param("pageIndex") Integer pageIndex);

    @Update("UPDATE userinfo SET currency = #{currency} WHERE openId = #{openId}")
    int updateCurrency(@Param("openId") String openId, @Param("currency") Integer currency);

    @Select("SELECT * FROM userinfo WHERE openId = #{openId}")
    UserVO getUserInfo(String openId);

    @Select("SELECT count(*) FROM follow WHERE openId = #{openId}")
    int getFollowNum(String openId);

    @Select("SELECT count(*) FROM follow WHERE beOpenId = #{openId}")
    int getFansNum(String openId);

    @Select("SELECT count(*) FROM dynamic WHERE openId = #{openId}")
    int getMyDynamicCount(String openId);

    @Select("SELECT dynamicId, topicId, content, longitude, latitude, location,sort, createTime FROM dynamic WHERE openId = " +
            "#{openId} ORDER BY dynamicId DESC LIMIT #{pageIndex}, 6")
    List<UserDynamicVO> getMyDynamicList(@Param("openId") String openId, @Param("pageIndex") Integer pageIndex);

    @Select("SELECT url, coverUrl, photoType FROM images WHERE dynamicId = #{dynamicId} ORDER BY createTime ASC")
    List<ImagesVO> getImageUrlList(long dynamicId);

    @Select("SELECT count(*) FROM liketable WHERE dynamicId = #{dynamicId}")
    int getLikeNum(long dynamicId);

    @Select("SELECT (SELECT count(*) FROM comment WHERE dynamicId = #{dynamicId}) + (SELECT count(*) FROM reply WHERE " +
            "dynamicId = #{dynamicId})")
    int getCommentNum(long dynamicId);

    @Select("SELECT * FROM userinfo WHERE userId = #{userId}")
    UserInfo getUserInfoByUserId(long userId);

    @Select("SELECT * FROM userinfo WHERE openId = #{openId}")
    UserInfo getUserInfoByOpenId(String openId);

    @Insert("INSERT INTO userinfo (userId, openId, nickName, avatarUrl, label, types) VALUES (#{userId}, #{openId}, " +
            "#{nickName}, #{avatarUrl}, #{label}, #{types})")
    int addFictitiousUser(UserInfo userInfo);

    @Select("SELECT * FROM topictable WHERE topicName = #{topicName}")
    TopicTable getTopic(String topicName);

    @Insert("INSERT INTO topictable (topicName) VALUES (#{topicName})")
    @Options(useGeneratedKeys = true, keyProperty = "topicId", keyColumn = "topicId")
    int addTopic(TopicTable topicTable);

    @Update("UPDATE topictable SET topicNum = topicNum + 1 WHERE topicName = #{topicName}")
    int updateTopic(TopicTable topicTable);

    @Update("UPDATE  userinfo set nickName = #{nickName},label=#{label},currency=#{currency},state=#{state},longitude=#{longitude},latitude=#{latitude}" +
            "WHERE openId = #{openId}")
    int editUserInfo(UserInfo userInfo);

    @Update("UPDATE  userinfo set avatarUrl=#{avatarUrl},nickName = #{nickName},label=#{label},currency=#{currency},state=#{state},longitude=#{longitude},latitude=#{latitude}" +
            "WHERE openId = #{openId}")
    int editUserInfoWithFile(UserInfo userInfo);

    @Insert("INSERT INTO dynamic (openId, topicId, content, longitude, latitude, location) VALUES " +
            "(#{openId}, #{topicId}, #{content}, #{longitude}, #{latitude}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "dynamicId", keyColumn = "dynamicId")
    int send(Dynamic dynamic);

    @Insert("INSERT INTO images (dynamicId, url, coverUrl, photoType) VALUES (#{dynamicId}, #{url}, #{coverUrl}, #{photoType})")
    int addPhoto(@Param("dynamicId") long dynamicId, @Param("url") String url, @Param("coverUrl") String coverUrl,
                 @Param("photoType") Integer photoType);

    @Delete("DELETE FROM dynamic WHERE dynamicId = #{dynamicId}")
    int delFictitiousDynamic(long dynamicId);

    @Delete("DELETE FROM images WHERE dynamicId = #{dynamicId}")
    void delPhoto(long dynamicId);

    @Delete("DELETE FROM at WHERE dynamicId = #{dynamicId}")
    void delAtFriends(@Param("dynamicId") long dynamicId);

    @Update("UPDATE dynamic SET sort = #{sort} where dynamicId =#{dynamicId}")
    int updateSort(@Param("dynamicId") long dynamicId,@Param("sort") int sort);

    @Delete("DELETE FROM comment WHERE dynamicId = #{dynamicId}")
    void delCommentByDynamicId(@Param("dynamicId") long dynamicId);

    @Delete("DELETE FROM reply WHERE dynamicId = #{dynamicId}")
    void delReplyByDynamicId(@Param("dynamicId") long dynamicId);

    @Delete("DELETE FROM liketable WHERE dynamicId = #{dynamicId}")
    void delLikeByDynamicId(@Param("dynamicId") long dynamicId);

    @Delete("DELETE FROM userinfo WHERE openId = #{openId}")
    int delLikeUser(@Param("openId") String openId);

    @Insert("<script>INSERT INTO liketable (openId, beOpenId, dynamicId, isRead) VALUE <foreach item='item' index='index' " +
            "collection='openIdList' separator=','>(#{item}, #{beOpenId}, #{dynamicId}, 1)</foreach></script>")
    void addFictitiousLike(@Param("beOpenId") String beOpenId, @Param("dynamicId") long dynamicId,
                           @Param("openIdList") List<String> openIdList);

    @Select("SELECT u.userId,u.nickName,u.avatarUrl,s.signUpFee,s.createTime FROM userinfo u JOIN signup s on u.openId=s.openId WHERE s.activityId =#{activityId}")
    List<UserInfo> getPeopleByactivityId(int activityId);

    @Insert("INSERT INTO userinfo (userId,openId,nickName,avatarUrl) values(#{userId},#{openId},#{nickName},#{avatarUrl})")
    int addUser(UserInfo userInfo);

    @Delete("DELETE FROM userinfo WHERE openId=#{openId}")
    int deleteUserInfo(String openId);
}
