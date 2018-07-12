package com.ddtkj.grabRedEnvelopeModule.Util;

import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_Application_Utils;
import com.utlis.lib.SharePre;

/**
 * 保存用户信息的SharePer
 * Created by ${杨重诚} on 2017/7/12.
 */

public class GrabRedEnvelopeModule_SharePer_UserInfo {
    //初始化SharedPreferences
    private static SharePre sharePre = null;
    private static GrabRedEnvelopeModule_SharePer_UserInfo single=null;
    private GrabRedEnvelopeModule_SharePer_UserInfo(){
        getSharePre();
    }
    //静态工厂方法
    public static GrabRedEnvelopeModule_SharePer_UserInfo getInstance() {
        if (single == null || sharePre == null) {
            single = new GrabRedEnvelopeModule_SharePer_UserInfo();
        }
        return single;
    }
    public static SharePre getSharePre() {
        sharePre = GrabRedEnvelopeModule_Application_Utils.getApplication().getUserSharedPreferences();
        return sharePre;
    }
}
