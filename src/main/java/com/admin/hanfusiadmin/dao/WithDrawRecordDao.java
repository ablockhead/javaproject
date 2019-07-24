package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.entity.WithDrawRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WithDrawRecordDao {

    @Insert("INSERT INTO withdrawrecord (activityId,bankAccount,bankName,applicant,withDrawMoney)" +
            "VALUES (#{activityId},#{bankAccount},#{bankName},#{applicant},#{withDrawMoney})")
    int addWithDraw(WithDrawRecord withDrawRecord);

    @Update("UPDATE withdrawrecord SET withdrawStatus = 1 WHERE id =#{id}")
    int updateWithDrawStatus(int id);

    @Select("SELECT COUNT(1) FROM withdrawrecord")
    int getWithRecord();

    @Select("SELECT * FROM withdrawrecord LIMIT #{pageIndex},6")
    List<WithDrawRecord> getAllWithRecord(int pageIndex);
}
