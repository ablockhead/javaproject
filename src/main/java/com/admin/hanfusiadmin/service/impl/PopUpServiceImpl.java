package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.PopUpDao;
import com.admin.hanfusiadmin.entity.PopUp;
import com.admin.hanfusiadmin.form.PopForm;
import com.admin.hanfusiadmin.service.PopUpService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import com.admin.hanfusiadmin.utils.UploadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PopUpServiceImpl implements PopUpService {

    @Autowired
    PopUpDao _PopUpDao;

    /**
     * 获取登录弹窗基本信息
     * @return 弹窗信息
     */
    @Override
    public ResultVO getPopUpInfo() {
        PopUp popUp  = _PopUpDao.getPopUpInfo();
        System.out.println(popUp);
        return  popUp != null ? ResultVOUtil.success(popUp) : ResultVOUtil.error(500000,"查询弹框信息失败");
    }

    @Override
    public ResultVO editPopUp(PopForm popTips) {
        System.out.println(popTips);
        int row = 0;
        if(popTips.getFile() != null){
            String url = UploadFileUtil.uploadFile(popTips.getFile(), "fictitious");
            row = _PopUpDao.editPopUrl(url,popTips.getPopType());
        }else{
            row = _PopUpDao.editPop(popTips.getPopType());
        }

        if(row>0){
            return  ResultVOUtil.success();
        }
        return ResultVOUtil.error(500000,"更新弹窗失败");
    }
}
