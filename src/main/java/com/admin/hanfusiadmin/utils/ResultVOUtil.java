package com.admin.hanfusiadmin.utils;


import com.admin.hanfusiadmin.VO.ResultVO;

/**
 * 描述：返回结果工具类
 */

public class ResultVOUtil {

    /**
     * 成功返回（带内容）
     * @param object 需要返回的对象
     * @return 返回结果
     */
    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

    /**
     * 成功返回（不带内容）
     * @return 返回结果
     */
    public static ResultVO success(){
        return success(null);
    }

    /**
     * 错误返回
     * @param code 状态
     * @param message 错误信息
     * @return 返回结果
     */
    public static ResultVO error(Integer code, String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(message);

        return resultVO;
    }
}
