package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.form.PopForm;

public interface PopUpService {

    ResultVO getPopUpInfo();

    ResultVO editPopUp(PopForm popTips);
}
