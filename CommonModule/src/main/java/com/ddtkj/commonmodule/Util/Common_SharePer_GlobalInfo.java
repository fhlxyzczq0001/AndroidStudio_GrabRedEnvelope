package com.ddtkj.commonmodule.Util;

import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.ddtkj.commonmodule.Base.Common_Application;
import com.ddtkj.commonmodule.MVP.Model.Bean.ResponseBean.Common_UserLogingPhoneCacheBean;
import com.ddtkj.commonmodule.Public.Common_SharedPreferences_Key;
import com.utlis.lib.SharePre;

import cn.jpush.android.api.JPushInterface;

public class Common_SharePer_GlobalInfo {
    //初始化SharedPreferences
    static SharePre sharePre = Common_Application.getInstance().getGlobalSharedPreferences();
    /**
     * 存放京东token
     */
    public static void sharePre_PutJD_Token(String jdPayToken) {
        sharePre.put(Common_SharedPreferences_Key.JD_PAY_TOKEN, jdPayToken);
        sharePre.commit();
    }

    /**
     * 获取京东token
     */
    public static String sharePre_GetJD_Token() {
        return sharePre.getString(Common_SharedPreferences_Key.JD_PAY_TOKEN, "");
    }
    /**
     * -存放登录成功的用户名
     *
     * @param userName
     */
    public static void sharePre_PutUserNameCache(String userName) {
        //判断是否已有登录账号
        Common_UserLogingPhoneCacheBean logingPhoneCacheBean = sharePre_GetUserNameCache();
        if (logingPhoneCacheBean == null) {
            logingPhoneCacheBean = new Common_UserLogingPhoneCacheBean();
        }
        logingPhoneCacheBean.getPhoneCache().add(userName);//添加登录名
        String json = JSONObject.toJSONString(logingPhoneCacheBean);//转化成json存放
        sharePre.put(Common_SharedPreferences_Key.USER_NAME_CACHE, json);//存放缓存对象
        sharePre.commit();
    }

    /**
     * 获取存放登录成功的用户名
     *
     * @return
     */
    public static Common_UserLogingPhoneCacheBean sharePre_GetUserNameCache() {
        String userName = sharePre.getString(Common_SharedPreferences_Key.USER_NAME_CACHE,
                "");
        if (userName != null && !userName.equals("")) {
            return JSONObject.parseObject(userName, Common_UserLogingPhoneCacheBean.class);
        }
        return null;
    }
    /**
     * 存放是否更新应用
     *
     * @param isUpdate
     */
    public static void sharePre_PutIsUpdate(boolean isUpdate) {
        sharePre.put(Common_SharedPreferences_Key.IS_UPDATE, isUpdate);
        sharePre.commit();
    }

    /**
     * 获取是否更新应用
     *
     * @return
     */
    public static boolean sharePre_GetIsUpdate() {
        return sharePre.getBoolean(Common_SharedPreferences_Key.IS_UPDATE, false);
    }

    /**
     * 存放是否接收推送
     *
     * @param isUpdate
     */
    public static void sharePre_PutIsPush(boolean isUpdate) {
        sharePre.put(Common_SharedPreferences_Key.IS_PUSH_STATE, isUpdate);
        sharePre.commit();
    }

    /**
     * 获取是否接收推送
     *
     * @return
     */
    public static boolean sharePre_GetIsPush() {
        return sharePre.getBoolean(Common_SharedPreferences_Key.IS_PUSH_STATE, true);
    }
    /**
     * 存放极光推送的信息
     *
     * @param jPusBundle
     */
    public static void sharePre_PutJpushBundle(Bundle jPusBundle) {
        if (jPusBundle != null) {
            if (jPusBundle.getString(JPushInterface.EXTRA_EXTRA) != null && !jPusBundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                String shareStr = jPusBundle.getString(JPushInterface.EXTRA_EXTRA) + "," + jPusBundle.getBoolean("isLoging", false);
                sharePre.put(Common_SharedPreferences_Key.JPUSH_BUNDLE, shareStr);
                sharePre.commit();
            }
        } else {
            sharePre.put(Common_SharedPreferences_Key.JPUSH_BUNDLE, "");
            sharePre.commit();
        }
    }

    /**
     * 获取极光推送的信息
     *
     * @return
     */
    public static Bundle sharePre_GetJpushBundle() {
        String jPusString = sharePre.getString(Common_SharedPreferences_Key.JPUSH_BUNDLE, "");
        Bundle bundle = new Bundle();
        if (!jPusString.isEmpty()) {
            String[] jPusStrings = jPusString.split(",");
            bundle.putString(JPushInterface.EXTRA_EXTRA, jPusStrings[0]);
            if (jPusStrings[1].contains("true")) {
                bundle.putBoolean("isLoging", true);
            } else {
                bundle.putBoolean("isLoging", false);
            }
            return bundle;
        }
        return null;
    }
}
