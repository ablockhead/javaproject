package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.ChildLogDao;
import com.admin.hanfusiadmin.entity.ChildLog;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.service.ChildLogService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChildLogServiceImpl implements ChildLogService {

    @Autowired
    ChildLogDao childLogDao;
    /**
     * 分页获取子账户的操作记录
     * @param pageIndex 当前页
     * @return 结果
     */
    @Override
    public ResultVO getAllChildLog(int pageIndex,String childName) {
        int totalCount = childLogDao.getChildLogCount(childName);
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }
        pageIndex = (pageIndex - 1) * 6;
        List<ChildLog> childLogList = childLogDao.getChildLogList(childName,pageIndex);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("childLogList", childLogList);

        return ResultVOUtil.success(map);
    }

    /**
     * 添加 子管理者 操作记录
     * @param childLog 操作记录信息
     * @return 结果
     */
    @Override
    public ResultVO addChildLog(ChildLog childLog) {
        System.out.println(childLog);
        return childLogDao.addChildLog(childLog) > 0 ? ResultVOUtil.success() :ResultVOUtil.error(5000,"添加操作记录");
    }

    /**
     * 删除子账户
     * @param username 子账户名称
     * @return 结果
     */
    @Override
    public ResultVO deleteSubAccount(String username) {

        return childLogDao.deleteSubAccount(username) > 0 ?
                ResultVOUtil.success():ResultVOUtil.error(5000,"删除失败");
    }
}
