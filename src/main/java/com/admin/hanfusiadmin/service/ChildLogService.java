package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.ChildLog;

public interface ChildLogService {

    ResultVO getAllChildLog(int pageIndex,String childName);

    ResultVO addChildLog(ChildLog childLog);

    ResultVO deleteSubAccount(String username);

}
