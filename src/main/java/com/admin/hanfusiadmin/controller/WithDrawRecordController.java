package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.WithDrawRecord;
import com.admin.hanfusiadmin.service.WithDrawRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("withDraw")
public class WithDrawRecordController {

    @Autowired
    WithDrawRecordService withDrawRecordService;

    /**
     * 添加提现申请
     * @param withDrawRecord 提现申请信息
     * @return 结果
     */
    @RequestMapping("addWithDraw")
    public ResultVO addWithDraw(WithDrawRecord withDrawRecord) {
        return withDrawRecordService.addWithDraw(withDrawRecord);
    }

    /**
     * 获取提现申请记录
     * @param pageIndex 当前页
     * @return 记录集合
     */
    @RequestMapping("getAllRecord")
    public ResultVO getAllRecord(int pageIndex){
        return withDrawRecordService.getAllRecord(pageIndex);
    }

    /**
     * 提现完成
     * @param id  提现记录Id
     * @param activityId 活动Id
     * @return 结果
     */
    @RequestMapping("updateRecord")
    public ResultVO updateRecord(int id,int activityId){
        return  withDrawRecordService.updateWithDraw(id,activityId);
    }
}
