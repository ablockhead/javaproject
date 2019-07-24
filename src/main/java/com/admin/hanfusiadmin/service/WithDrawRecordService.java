package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.WithDrawRecord;

public interface WithDrawRecordService {

    ResultVO addWithDraw(WithDrawRecord withDrawRecord);

    ResultVO getAllRecord(int pageIndex);

    ResultVO updateWithDraw(int id,Integer activityId);

}
