package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.entity.Banner;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface BannerDao {

    @Select("SELECT count(*) FROM banner")
    int getBannerCount();

    @Select("SELECT * FROM banner ORDER BY sort ASC LIMIT #{pageIndex}, 6")
    List<Banner> getBannerList(Integer pageIndex);

    @Insert("INSERT INTO banner (title, url, pages, sort) VALUES (#{title}, #{url}, #{pages}, #{sort})")
    int addBanner(Banner banner);

    @Delete("DELETE FROM banner WHERE bannerId = #{bannerId}")
    int delBanner(Integer bannerId);

    @Update("UPDATE banner SET sort = #{sort} WHERE bannerId = #{bannerId}")
    int updateSort(@Param("bannerId") Integer bannerId, @Param("sort") Integer sort);

    @Select("SELECT * FROM banner WHERE bannerId = #{bannerId}")
    Banner getBanner(Integer bannerId);
}
