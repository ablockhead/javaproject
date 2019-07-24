package com.admin.hanfusiadmin.utils;

import com.alibaba.fastjson.JSONObject;

import static com.admin.hanfusiadmin.utils.DataUtil.APPID;
import static com.admin.hanfusiadmin.utils.DataUtil.APPSECRET;

/**
 * 获取token工具类
 */

public class TokenUtil {

    public static JSONObject requestToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
        return HttpRequestUtil.sendGet(url);
    }
}
