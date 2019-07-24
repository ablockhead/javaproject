package com.admin.hanfusiadmin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class WithDrawRecord {

    // 提现记录Id
    private int id;

    // 活动Id
    private int activityId;

    // 提现状态
    private int withdrawStatus;

    // 银行账户
    private String bankAccount;

    // 银行名称
    private String bankName;

    // 申请账户名
    private String applicant;

    // 提现状态
    private int status;

    // 申请金额
    private int withDrawMoney;

    // 申请时间
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private String createtime;

}
