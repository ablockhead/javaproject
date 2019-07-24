package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.ChildLog;
import com.admin.hanfusiadmin.service.ChildLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("child")
public class ChildLogController {

    @Autowired
    ChildLogService childLogService;

    /**
     * 分页获取子账户的操作记录
     * @param pageIndex 当前页
     * @return 结果
     */
    @RequestMapping("getAllChildLog")
    public ResultVO getAllChildLog(@RequestParam("childName") String childName,@RequestParam("pageIndex") int pageIndex){
        return childLogService.getAllChildLog(pageIndex,childName);
    }

    /**
     * 添加 子管理者 操作记录
     * @param childLog 操作记录信息
     * @return 结果
     */
    @RequestMapping("addChildLog")
    public ResultVO addChildLog(ChildLog childLog){
        return childLogService.addChildLog(childLog);
    }

    @RequestMapping("deleteSubAccount")
    public ResultVO deleteSubAccount(String username){
        return childLogService.deleteSubAccount(username);
    }

}
