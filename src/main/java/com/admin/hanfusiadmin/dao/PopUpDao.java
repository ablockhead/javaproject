package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.entity.PopUp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PopUpDao {

    @Select("SELECT * FROM popup WHERE popId = 1")
    PopUp getPopUpInfo();

    @Update("UPDATE popup SET popType = #{popType} where popId = 1")
    int editPop(int popType);

    @Update("UPDATE popup SET popImg=#{popImg},popType = #{popType} where popId = 1")
    int editPopUrl(@Param("popImg") String popImg,@Param("popType") int popType);
}
