package com.ddtkj.grabRedEnvelopeModule.Util;

import com.ddtkj.grabRedEnvelopeModule.Base.GrabRedEnvelopeModule_Application_Utils;
import com.utlis.lib.SharePre;

/**
 * 保存全局信息的SharePer
 */
public class GrabRedEnvelopeModule_SharePer_GlobalInfo {
    //初始化SharedPreferences
    private static SharePre sharePre = null;
    private static GrabRedEnvelopeModule_SharePer_GlobalInfo single = null;

    private GrabRedEnvelopeModule_SharePer_GlobalInfo() {
        getSharePre();
    }

    //静态工厂方法
    public static GrabRedEnvelopeModule_SharePer_GlobalInfo getInstance() {
        if (single == null || sharePre == null) {
            single = new GrabRedEnvelopeModule_SharePer_GlobalInfo();
        }
        return single;
    }

    public static SharePre getSharePre() {
        sharePre = GrabRedEnvelopeModule_Application_Utils.getApplication().getGlobalSharedPreferences();
        return sharePre;
    }
}
