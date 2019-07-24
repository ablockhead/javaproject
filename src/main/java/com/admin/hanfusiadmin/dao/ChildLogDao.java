package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.entity.ChildLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Indexed;

import java.util.List;

@Mapper
@Component
public interface ChildLogDao {

    @Select("SELECT * FROM childlog WHERE childName=#{childName} ORDER BY createtime ASC LIMIT #{pageIndex}, 6 ")
    List<ChildLog> getChildLogList(@Param("childName") String childName,@Param("pageIndex") Integer pageIndex);

    @Select("SELECT COUNT(1) FROM childlog WHERE childName=#{childName}")
    int getChildLogCount(@Param("childName") String childName);

    @Insert("INSERT INTO childlog (childName,handleDetail,types,typesId) values(#{childName},#{handleDetail},#{types},#{typesId})")
    int addChildLog(ChildLog childLog);

    @Select("SELECT count(1) FROM childlog WHERE types=#{types} AND childName=#{childName}")
    int getActivityCount(@Param("types") int types,@Param("childName") String childName);

    @Delete("DELETE FROM adminuser WHERE userName=#{userName}")
    int deleteSubAccount(String username);

}
