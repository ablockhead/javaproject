package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.ClassifyDao;
import com.admin.hanfusiadmin.dao.WithDrawRecordDao;
import com.admin.hanfusiadmin.entity.CircleActivity;
import com.admin.hanfusiadmin.entity.WithDrawRecord;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.service.WithDrawRecordService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WithDrawRecordServiceImpl implements WithDrawRecordService {

    @Autowired
    WithDrawRecordDao withDrawRecordDao;

    @Autowired
    ClassifyDao classifyDao;
    /**
     * 添加提现申请
     * @param withDrawRecord 提现申请信息
     * @return 结果
     */
    @Override
    public ResultVO addWithDraw(WithDrawRecord withDrawRecord) {

        classifyDao.updateStatus(1,withDrawRecord.getActivityId());
        return withDrawRecordDao.addWithDraw(withDrawRecord) > 0 ?
                ResultVOUtil.success() : ResultVOUtil.error(5000,"提现申请失败稍后重试");
    }

    /**
     * 获取提现申请记录
     * @param pageIndex 当前页
     * @return 记录集合
     */
    @Override
    public ResultVO getAllRecord(int pageIndex) {
        int totalCount = withDrawRecordDao.getWithRecord();
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }
        pageIndex = (pageIndex - 1) * 6;
        List<WithDrawRecord> withDrawRecords = withDrawRecordDao.getAllWithRecord(pageIndex);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("withDrawRecords", withDrawRecords);
        return ResultVOUtil.success(map);
    }

    /**
     * 提现完成
     * @param id  提现记录Id
     * @param activityId 活动Id
     * @return 结果
     */
    @Override
    public ResultVO updateWithDraw(int id,Integer activityId) {
        classifyDao.updateStatus(2,activityId);
        return withDrawRecordDao.updateWithDrawStatus(id) > 0 ?
                ResultVOUtil.success(): ResultVOUtil.error(5000,"提现完成");
    }
}
