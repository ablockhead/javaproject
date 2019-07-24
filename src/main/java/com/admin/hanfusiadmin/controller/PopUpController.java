package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.form.PopForm;
import com.admin.hanfusiadmin.service.PopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("popup")
public class PopUpController {

    @Autowired
    PopUpService _popUpService;

    @RequestMapping("getPopUpInfo")
    public ResultVO getPopUp(){
        return _popUpService.getPopUpInfo();
    }

    @RequestMapping("editPopUp")
    public ResultVO updatePopUp(PopForm popForm){
        return  _popUpService.editPopUp(popForm);
    }

}
