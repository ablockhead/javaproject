package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.entity.CircleActivity;
import com.admin.hanfusiadmin.entity.CircleClass;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CircleClassDao {

    @Select("SELECT * FROM circle_class WHERE deleteid = 0 ORDER BY createTime DESC LIMIT #{pageIndex},6")
    List<CircleClass> getCircleClassList(int pageIndex);

    @Select("SELECT c.* FROM circle_class c JOIN childlog chi ON c.classId = chi.typesId WHERE chi.types = 3 AND chi.childName = #{childName} ORDER BY c.createTime DESC LIMIT #{pageIndex},6")
    List<CircleClass> getCircleChildClassList(@Param("childName") String childName,@Param("pageIndex") int pageIndex);
    @Select("SELECT * FROM circle_class")
    List<CircleClass> getAllCircleClassList();

    @Select("SELECT * FROM circle_activity WHERE classId=#{classId} ORDER BY createtime ASC LIMIT #{pageIndex}, 6 ")
    List<CircleActivity> getActiviByIndexAndClassId(@Param("pageIndex")int pageIndex,@Param("classId") int classId);

    @Select("SELECT COUNT(1) FROM circle_activity WHERE classId=#{classId}")
    int getActiviCount(@Param("classId") int classId);

    @Select("SELECT COUNT(1) FROM circle_class")
    int getCircleClasCount();


    @Select("SELECT * FROM circle_class WHERE classId=#{classId}")
    CircleClass getCircleInfo(int classId);

    @Delete("update circle_class set deleteid = 1 where classId = #{classId}")
    int deleteCircleClass(int classId);

    @Update("UPDATE circle_class set circle_name = #{circle_name},image_url = #{image_url},uid = #{uid}," +
            "circle_presentation = #{circle_presentation},master_presentation = #{master_presentation},limits = #{limits},addV = #{addV} WHERE classId=#{classId}")
    int updateCircleInfo(CircleClass circleClass);
}
